; This tool translates the latest Minecraft protocol specification from wiki.vg/protocol into mostly valid Java source code for quick updating.

#include <Array.au3>
#include <Wickersoft_HTTP.au3>

DirCreate(@DesktopDir & "\packet")
DirCreate(@DesktopDir & "\packet\clientbound")
DirCreate(@DesktopDir & "\packet\serverbound")
FileDelete(@DesktopDir & "\packet\clientbound\*")
FileDelete(@DesktopDir & "\packet\serverbound\*")
FileDelete(@DesktopDir & "\Packet.java")


TCPStartup()

$a = _http("wiki.vg", "protocol")
$html = BinaryToString($a[0], 4)
$relevantHtml = stringextract($html, '<span class="mw-headline" id="Clientbound_2">Clientbound</span>', 'NewPP limit report')
$tableHtml = stringextractall($relevantHtml, '<span class="mw-headline" id=', "</table>")



Dim $packets[UBound($tableHtml)]
$iPacket = 0

For $i = 0 To UBound($tableHtml) - 1
	Dim $packet[5]

	$name = stringextract($tableHtml[$i], '">', '</')
	$lines = stringextractall($tableHtml[$i], "<tr>", "</tr>")
	$name = StringRegExpReplace($name, "\(.+\)", "")
	$name = StringRegExpReplace($name, "\W", "")

	$packet[1] = StringStripWS($name, 8)
	$packet[4] = stringextract($tableHtml[$i], "<p>", "</p>")

	;ConsoleWrite("Parsing section " & $name & @CRLF)

	$cols = stringextractall($lines[0], "<th>", "</th>")
	For $k = 0 To UBound($cols) - 1
		$cols[$k] = StringStripWS($cols[$k], 3)
	Next

	If UBound($cols) <> 6 Then
		ConsoleWrite("-> Section " & $name & " has weird table (" & UBound($cols) & "): " & $lines[0] & @CRLF)
	EndIf

	If UBound($cols) = 0 Then ContinueLoop

	If $cols[0] <> "Packet ID" Then
		ConsoleWrite("-> Section " & $name & " has no table with packet ID. Instead: " & $cols[0] & @CRLF)
		ContinueLoop
	EndIf


	$structure = parseStructure($lines)
	If @error Then
		$javacode = generateClassFromAncestor($packet)
		If $packet[2] = "Client" Then
			$file = FileOpen(@DesktopDir & "\packet\clientbound\Packet" & $packet[0] & $packet[1] & ".java", 2)
			FileWrite($file, $javacode)
			FileClose($file)
		Else
			$file = FileOpen(@DesktopDir & "\packet\serverbound\Packet" & $packet[0] & $packet[1] & ".java", 2)
			FileWrite($file, $javacode)
			FileClose($file)
		EndIf
		ContinueLoop
	EndIf

	$packet[3] = $structure
	$packets[$iPacket] = $packet
	$iPacket += 1


	$javacode = ""
	If $packet[2] = "Client" Then
		$javacode = generateClientboundClass($packet)
		$file = FileOpen(@DesktopDir & "\packet\clientbound\Packet" & $packet[0] & $packet[1] & ".java", 2)
		FileWrite($file, $javacode)
		FileClose($file)
	Else
		$javacode = generateServerboundClass($packet)
		$file = FileOpen(@DesktopDir & "\packet\serverbound\Packet" & $packet[0] & $packet[1] & ".java", 2)
		FileWrite($file, $javacode)
		FileClose($file)
	EndIf

Next

ConsoleWrite(@CRLF & @CRLF & @CRLF & @CRLF)

$file = FileOpen(@DesktopDir & "\Packet.java", 2)
FileWrite($file, generatePacketIdMap($packets))
FileClose($file)

ConsoleWrite($iPacket & "/" & UBound($tableHtml) & " packets successfully parsed" & @CRLF)

Func findClassWithSameName($packetid, $packetName)
	$file = FileFindFirstFile(@WorkingDir & "\Packet??" & $packetName & ".java")
	If @error Then Return promptClassFile($packetid, $packetName)

	$result = FileFindNextFile($file)
	If @error Then Return promptClassFile($packetid, $packetName)

	FileFindNextFile($file)
	If @error Then Return @WorkingDir & "\" & $result
	Return promptClassFile($packetid, $packetName)
EndFunc   ;==>findClassWithSameName

Func promptClassFile($packetid, $packetName)
	$oldfilepath = FileOpenDialog("Select Ancestor for Packet" & $packetid & $packetName, @WorkingDir, "Java Source (*.java)")
	If @error Then Return ""
	Return $oldfilepath
EndFunc   ;==>promptClassFile

Func parseStructure(ByRef $lines)
	Dim $structure[UBound($lines) - 1][3]
	For $j = 1 To UBound($lines) - 1
		$cols = stringextractall($lines[$j], ">", "</td>")
		For $k = 0 To UBound($cols) - 1
			$cols[$k] = StringStripWS($cols[$k], 3)
		Next

		;_ArrayDisplay($cols)

		If $j = 1 Then
			If UBound($cols) >= 3 Then
				$packet[0] = StringTrimLeft($cols[0], 2)
				If $packet[0] = "00" Then FileChangeDir("C:\htdocs\projects\javastuff\zbot\src\main\java\zedly\zbot\network")
				$packet[2] = $cols[2]
			EndIf

			If UBound($cols) < 5 Or UBound($cols) > 6 Then
				ConsoleWrite("-> Section " & $name & " has weird table (" & UBound($cols) & "): " & $lines[$j] & @CRLF)
				Return SetError(1, 0, "")
			EndIf

			If getZBotFieldType($cols[4]) = "" Then
				ConsoleWrite("Packet " & $name & " contains unknown data type: " & $cols[4] & @CRLF)
				Return SetError(1, 0, "")
			EndIf

			$cols[3] = StringStripWS($cols[3], 8)

			$structure[$j - 1][0] = $cols[4]
			$structure[$j - 1][1] = StringLower(StringLeft($cols[3], 1)) & StringTrimLeft($cols[3], 1)
			If UBound($cols) >= 6 Then $structure[$j - 1][2] = $cols[5]
		Else
			If UBound($cols) < 2 Or UBound($cols) > 3 Then
				ConsoleWrite("-> Section " & $name & " has weird table (" & UBound($cols) & "): " & $lines[$j] & @CRLF)
				Return SetError(1, 0, "")
			EndIf
			If getZBotFieldType($cols[1]) = "" Then
				ConsoleWrite("Packet " & $name & " contains unknown data type: " & $cols[1] & @CRLF)
				Return SetError(1, 0, "")
			EndIf

			$cols[0] = StringStripWS($cols[0], 8)

			$structure[$j - 1][0] = $cols[1]
			$structure[$j - 1][1] = StringLower(StringLeft($cols[0], 1)) & StringTrimLeft($cols[0], 1)
			If UBound($cols) >= 3 Then $structure[$j - 1][2] = $cols[2]
		EndIf
	Next
	Return $structure
EndFunc   ;==>parseStructure

Func generateClassFromAncestor(ByRef $packet)

	$oldfilepath = findClassWithSameName($packet[0], $packet[1])

	If $oldfilepath = "" Then
		$javacode = "package zedly.zbot.network.packet." & StringLower($packet[2]) & "bound;" & @CRLF & _
				"public class Packet" & $packet[0] & $packet[1] & " implements " & $packet[2] & "BoundPacket {" & @CRLF

		If $packet[2] = "Client" Then
			$javacode &= "    public void readPacket(ExtendedDataInputStream dis) throws IOException {}" & @CRLF & @CRLF
		Else
			$javacode &= "    @Override" & @CRLF
			$javacode &= "    public void writePacket(ExtendedDataOutputStream dos) throws IOException {}" & @CRLF & @CRLF
			$javacode &= "    @Override" & @CRLF
			$javacode &= "    public int opCode() { return 0x" & $packet[0] & "; }" & @CRLF & @CRLF
		EndIf
		$javacode &= "} Skeleton with unreadable structure and no ancestor. Implement manually"
		Return $javacode
	EndIf

	$oldCode = FileRead($oldfilepath)
	$oldClassName = stringextract($oldCode, "public class ", " implements")
	$oldCode = StringReplace($oldCode, $oldClassName, "Packet" & $packet[0] & $packet[1])
	$opcode = stringextract($oldCode, "return 0x", ";")
	If $opcode <> "" Then
		$oldCode = StringReplace($oldCode, "0x" & $opcode, "0x" & $packet[0])
	EndIf
	$oldCode &= "Refactored ancestor. Review data strcuture"
	Return $oldCode
EndFunc   ;==>generateClassFromAncestor

Func generatePacketIdMap(ByRef $packets)
	$javacode = "    static {" & @CRLF
	$javacode &= "        clientBoundPackets = new HashMap<>();" & @CRLF
	$javacode &= "        serverBoundPackets = new HashMap<>();" & @CRLF

	For $i = 0 To $iPacket - 1
		$packet = $packets[$i]
		If $packet[2] = "Client" Then
			$javacode &= "      clientBoundPackets.put(0x" & $packet[0] & ", Packet" & $packet[0] & $packet[1] & ".class);" & @CRLF
		Else
			$javacode &= "      serverBoundPackets.put(0x" & $packet[0] & ", Packet" & $packet[0] & $packet[1] & ".class);" & @CRLF
		EndIf
	Next

	$javacode &= "    }" & @CRLF
	Return $javacode
EndFunc   ;==>generatePacketIdMap


Func generateClientboundClass(ByRef $packet)
	$structure = $packet[3]

	$javacode = ""

	$javacode &= "/**" & @CRLF
	$javacode &= "* " & $packet[4]
	$javacode &= "*/" & @CRLF & @CRLF

	$javacode &= "public class Packet" & $packet[0] & $packet[1] & " implements ClientBoundPacket {" & @CRLF


	For $j = 0 To UBound($structure, 1) - 1
		$javacode &= "    private " & getZBotFieldType($structure[$j][0]) & " " & $structure[$j][1] & ";"
		If $structure[$j][2] <> "" Then $javacode &= "  // " & $structure[$j][2]
		$javacode &= @CRLF
	Next

	$javacode &= @CRLF & @CRLF

	$javacode &= "    @Override" & @CRLF
	$javacode &= "    public void readPacket(ExtendedDataInputStream dis, int packetLen) throws IOException {" & @CRLF

	For $j = 0 To UBound($structure, 1) - 1
		$javacode &= "        " & $structure[$j][1] & " = dis." & getZBotReadMethodType($structure[$j][0]) & "();" & @CRLF
	Next

	$javacode &= "    }" & @CRLF & @CRLF


	$oldfilepath = findClassWithSameName($packet[0], $packet[1])

	If $oldfilepath <> "" Then
		ConsoleWrite("Ancestor class selected" & @CRLF)
		$oldCode = FileRead($oldfilepath)

		$imports = stringextract($oldCode, "package", "public")
		$javacode = "package " & $imports & @CRLF & $javacode

		$processMethod = stringextract($oldCode, "public void process(GameContext context) {", @LF & "    }")
		If $processMethod <> "" Then
			ConsoleWrite("Ancestor code not empty-" & @CRLF)
			$javacode &= "    @Override" & @CRLF
			$javacode &= "    public void process(GameContext context) {"
			$javacode &= $processMethod
			$javacode &= "    }" & @CRLF & @CRLF
		EndIf
	Else
		$javacode = "package zedly.zbot.network.packet.clientbound;" & @CRLF & @CRLF & _
				"import java.io.IOException;" & @CRLF & _
				"import zedly.zbot.network.ExtendedDataInputStream;" & @CRLF & $javacode
	EndIf

	$javacode &= "}" & @CRLF
	Return $javacode
EndFunc   ;==>generateClientboundClass

Func generateServerboundClass($packet)
	$structure = $packet[3]

	$oldfilepath = findClassWithSameName($packet[0], $packet[1])

	If $oldfilepath <> "" Then
		ConsoleWrite("Ancestor class selected" & @CRLF)
		$oldCode = FileRead($oldfilepath)
		$imports = stringextract($oldCode, "package", "public")
		$javacode = "package " & $imports & @CRLF
	Else
		$javacode = "package zedly.zbot.network.packet.clientbound;" & @CRLF & @CRLF & _
				"import java.io.IOException;" & @CRLF & _
				"import zedly.zbot.network.ExtendedDataInputStream;" & @CRLF
	EndIf

	$javacode &= "/**" & @CRLF
	$javacode &= "* " & $packet[4]
	$javacode &= "*/" & @CRLF & @CRLF

	$javacode &= "public class Packet" & $packet[0] & $packet[1] & " implements ServerBoundPacket {" & @CRLF


	For $j = 0 To UBound($structure, 1) - 1
		$javacode &= "    private final " & getZBotFieldType($structure[$j][0]) & " " & $structure[$j][1] & ";"
		If $structure[$j][2] <> "" Then $javacode &= "  // " & $structure[$j][2]
		$javacode &= @CRLF
	Next

	$javacode &= @CRLF & @CRLF

	$javacode &= "    public Packet" & $packet[0] & $packet[1] & "("
	For $j = 0 To UBound($structure, 1) - 2
		$javacode &= getZBotFieldType($structure[$j][0]) & " " & $structure[$j][1] & ", "
	Next
	If UBound($structure, 1) >= 1 Then $javacode &= getZBotFieldType($structure[UBound($structure, 1) - 1][0]) & " " & $structure[UBound($structure, 1) - 1][1]

	$javacode &= ") {" & @CRLF
	For $j = 0 To UBound($structure, 1) - 1
		$javacode &= "        this." & $structure[$j][1] & " = " & $structure[$j][1] & ";" & @CRLF
	Next

	$javacode &= "    }" & @CRLF & @CRLF


	$javacode &= "    @Override" & @CRLF
	$javacode &= "    public int opCode() {" & @CRLF
	$javacode &= "        return 0x" & $packet[0] & ";" & @CRLF
	$javacode &= "    }" & @CRLF & @CRLF


	$javacode &= "    @Override" & @CRLF
	$javacode &= "    public void writePacket(ExtendedDataOutputStream dos) throws IOException {" & @CRLF

	For $j = 0 To UBound($structure, 1) - 1
		$javacode &= "        dos." & getZBotWriteMethodType($structure[$j][0]) & "(" & $structure[$j][1] & ");" & @CRLF
	Next

	$javacode &= "    }" & @CRLF
	$javacode &= "}" & @CRLF
	Return $javacode
EndFunc   ;==>generateServerboundClass

Func getZBotFieldType($dataType)
	If $dataType = "Boolean" Then Return "boolean"
	If $dataType = "Unsigned Byte" Then Return "int"
	If $dataType = "Byte" Then Return "int"
	If $dataType = "Byte Enum" Then Return "int"
	If $dataType = "Short" Then Return "int"
	If $dataType = "Unsigned Short" Then Return "int"
	If $dataType = "Int" Then Return "int"
	If $dataType = "Int Enum" Then Return "int"
	If $dataType = "Unsigned Int" Then Return "int"
	If $dataType = "Long" Then Return "long"
	If $dataType = "Float" Then Return "double"
	If $dataType = "Double" Then Return "double"
	If StringInStr($dataType, "String") Then Return "String"
	If $dataType = "Chat" Then Return "String"
	If StringInStr($dataType, "Chat") Then Return "String"
	If $dataType = "Identifier" Then Return "String"
	If $dataType = "VarInt" Then Return "int"
	If $dataType = "VarInt Enum" Then Return "int"
	If $dataType = "VarLong" Then Return "long"
	If StringInStr($dataType, "Entity Metadata") Then Return "HashMap<Integer, EntityMeta>"
	If $dataType = "Slot" Then Return "ItemStack"
	If StringInStr($dataType, "Slot") Then Return "ItemStack"
	If StringInStr($dataType, "NBT Tag") Then Return "NBTBase"
	If $dataType = "Position" Then Return "Location"
	If $dataType = "Location" Then Return "Location"
	If $dataType = "Angle" Then Return "int"
	If $dataType = "UUID" Then Return "UUID"
	Return ""
EndFunc   ;==>getZBotFieldType

Func getZBotReadMethodType($dataType)
	If $dataType = "Boolean" Then Return "readBoolean"
	If $dataType = "Unsigned Byte" Then Return "readUnsignedByte"
	If $dataType = "Byte" Then Return "readByte"
	If $dataType = "Byte Enum" Then Return "readByte"
	If $dataType = "Short" Then Return "readShort"
	If $dataType = "Unsigned Short" Then Return "readUnsignedShort"
	If $dataType = "Int" Then Return "readInt"
	If $dataType = "Int Enum" Then Return "readInt"
	If $dataType = "Unsigned Int" Then Return "readInt"
	If $dataType = "Long" Then Return "readLong"
	If $dataType = "Float" Then Return "readFloat"
	If $dataType = "Double" Then Return "readDouble"
	If StringInStr($dataType, "String") Then Return "readString"
	If $dataType = "Chat" Then Return "readString"
	If StringInStr($dataType, "Chat") Then Return "readString"
	If $dataType = "Identifier" Then Return "readString"
	If $dataType = "VarInt" Then Return "readVarInt"
	If $dataType = "VarInt Enum" Then Return "readVarInt"
	If $dataType = "VarLong" Then Return "readVarLong"
	If StringInStr($dataType, "Entity Metadata") Then Return "readEntityMetaData"
	If $dataType = "Slot" Then Return "readSlot"
	If StringInStr($dataType, "Slot") Then Return "readSlot"
	If StringInStr($dataType, "NBT Tag") Then Return "readNBT"
	If $dataType = "Position" Then Return "readPosition"
	If $dataType = "Location" Then Return "readPosition"
	If $dataType = "Angle" Then Return "readUnsignedByte"
	If $dataType = "UUID" Then Return "readUUID"
	Return ""
EndFunc   ;==>getZBotReadMethodType

Func getZBotWriteMethodType($dataType)
	If $dataType = "Boolean" Then Return "writeBoolean"
	If $dataType = "Unsigned Byte" Then Return "writeByte"
	If $dataType = "Byte" Then Return "writeByte"
	If $dataType = "Byte Enum" Then Return "writeByte"
	If $dataType = "Short" Then Return "writeShort"
	If $dataType = "Unsigned Short" Then Return "writeShort"
	If $dataType = "Int" Then Return "writeInt"
	If $dataType = "Int Enum" Then Return "writeInt"
	If $dataType = "Unsigned Int" Then Return "writeInt"
	If $dataType = "Long" Then Return "writeLong"
	If $dataType = "Float" Then Return "writeFloat"
	If $dataType = "Double" Then Return "writeDouble"
	If StringInStr($dataType, "String") Then Return "writeString"
	If $dataType = "Chat" Then Return "writeString"
	If StringInStr($dataType, "Chat") Then Return "writeString"
	If $dataType = "Identifier" Then Return "writeString"
	If $dataType = "VarInt" Then Return "writeVarInt"
	If $dataType = "VarInt Enum" Then Return "writeVarInt"
	If $dataType = "VarLong" Then Return "writeVarLong"
	If StringInStr($dataType, "Entity Metadata") Then Return "writeEntityMetaData"
	If $dataType = "Slot" Then Return "writeSlot"
	If StringInStr($dataType, "Slot") Then Return "writeSlot"
	If StringInStr($dataType, "NBT Tag") Then Return "writeNBT"
	If $dataType = "Position" Then Return "writePosition"
	If $dataType = "Location" Then Return "writePosition"
	If $dataType = "Angle" Then Return "writeByte"
	If $dataType = "UUID" Then Return "writeUUID"
	Return ""
EndFunc   ;==>getZBotWriteMethodType

Func dumpFile($name, $content)
	FileDelete(@DesktopDir & "\" & $name & ".txt")
	FileWrite(@DesktopDir & "\" & $name & ".txt", $content)
EndFunc   ;==>dumpFile
