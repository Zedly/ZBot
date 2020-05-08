#include<WinHTTP.au3>

Global $hSession = _WinHTTPOpen()
Global $Wi_ReadBufferSize = 262144

Func _http($Domain_Name, $URI = "", $content = "", $http_cookies = "", $Additional_Header = "")
	Local $HTTP_Header, $Received_Content, $Content_Length, $Received_Piece, $Previous_Size, $Content_Type = "application/x-www-form-urlencoded"
	Dim $Return[2]
	Dim $aReceived_Content[2]
	$init = TimerInit()
	If StringInStr($Domain_Name, ":") Then
		$Domain_Name = StringSplit($Domain_Name, ":")
		$Port = $Domain_Name[2]
		$Domain_Name = $Domain_Name[1]
	Else
		$Port = 80
	EndIf
	Local $retr = ""

	If $content <> "" Then
		$hConnect = _WinHttpConnect($hSession, $Domain_Name, $Port)
		If @error Then
			$Return[1] = "Connection refused"
			Return $Return
		EndIf
		$hRequest = _WinHTTPOpenRequest($hConnect, "POST", $URI, Default, Default, Default, 0x40)
		$sHeader = "Content-Type: application/x-www-form-urlencoded; charset=UTF-8" & @CRLF & _
				"User-Agent: Mozilla/5.0 (Windows NT 6.3; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0" & @CRLF & _
				$Additional_Header
		_WinHttpSendRequest($hRequest, $sHeader, $content)
		If @error Then
			$Return[1] = "Connection refused"
			Return $Return
		EndIf
		_WinHttpReceiveResponse($hRequest)
		If @error Then
			$Return[1] = "Connection refused"
			Return $Return
		EndIf
		$headers = _WinHttpQueryHeaders($hRequest)
		$Received_Content = Binary("")
		Do
			$Received_Content &= _WinHttpReadData($hRequest, 2, $Wi_ReadBufferSize)
		Until @error
	Else
		$hConnect = _WinHttpConnect($hSession, $Domain_Name, $Port)
		If @error Then
			$Return[1] = "Connection refused"
			Return $Return
		EndIf
		$hRequest = _WinHTTPOpenRequest($hConnect, "GET", $URI, Default, Default, Default, 0x40)
		$sHeader = "Content-Type: application/x-www-form-urlencoded; charset=UTF-8" & @CRLF & _
				"User-Agent: Mozilla/5.0 (Windows NT 6.3; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0" & @CRLF & _
				$Additional_Header
		_WinHttpSendRequest($hRequest, $sHeader)
		If @error Then
			$Return[1] = "Connection refused"
			Return $Return
		EndIf
		_WinHttpReceiveResponse($hRequest)
		If @error Then
			$Return[1] = "Connection refused"
			Return $Return
		EndIf
		$headers = _WinHttpQueryHeaders($hRequest)
		$Received_Content = Binary("")
		Do
			$Received_Content &= _WinHttpReadData($hRequest, 2, $Wi_ReadBufferSize)
		Until @error
	EndIf
	$Return[0] = $Received_Content
	$Return[1] = $headers
	Return $Return
EndFunc   ;==>_http

Func _https($Domain_Name, $URI = "", $content = "", $http_cookies = "", $Additional_Header = "")
	Local $HTTP_Header, $Received_Content, $Content_Length, $Received_Piece, $Previous_Size, $Content_Type = "application/x-www-form-urlencoded"
	Dim $Return[2]
	Dim $aReceived_Content[2]
	$init = TimerInit()
	If StringInStr($Domain_Name, ":") Then
		$Domain_Name = StringSplit($Domain_Name, ":")
		$Port = $Domain_Name[2]
		$Domain_Name = $Domain_Name[1]
	Else
		$Port = 443
	EndIf
	Local $retr = ""

	If $content <> "" Then
		$hConnect = _WinHttpConnect($hSession, $Domain_Name, $Port)
		If @error Then
			$Return[1] = "Connection refused 0"
			Return $Return
		EndIf
		$hRequest = _WinHTTPOpenRequest($hConnect, "POST", $URI, Default, Default, Default, 0x800040)
		$sHeader = "Content-Type: application/x-www-form-urlencoded" & @CRLF & _
				"User-Agent: Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36" & @CRLF & _
				"Content-Length: " & BinaryLen($content)
		If $http_cookies <> "" Then $sHeader &= @CRLF & "Cookie: " & $http_cookies
		If $Additional_Header <> "" Then $sHeader &= @CRLF & $Additional_Header
		_WinHttpSendRequest($hRequest, $sHeader, $content)
		If @error Then
			$Return[1] = "Connection refused 1"
			Return $Return
		EndIf
		_WinHttpReceiveResponse($hRequest)
		If @error Then
			$Return[1] = "Connection refused 2"
			Return $Return
		EndIf
		$headers = _WinHttpQueryHeaders($hRequest)
		$Received_Content = _WinHttpReadData($hRequest, 2, $Wi_ReadBufferSize)
	Else
		$hConnect = _WinHttpConnect($hSession, $Domain_Name, $Port)
		If @error Then
			$Return[1] = "Connection refused 3"
			Return $Return
		EndIf
		$hRequest = _WinHTTPOpenRequest($hConnect, "GET", $URI, Default, Default, Default, 0x800040)
		$sHeader = "User-Agent: Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36" & @CRLF & _
				$Additional_Header
		If $http_cookies <> "" Then $sHeader &= @CRLF & "Cookie: " & $http_cookies
		_WinHttpSendRequest($hRequest, $sHeader)
		If @error Then
			$Return[1] = "Connection refused 4"
			Return $Return
		EndIf
		_WinHttpReceiveResponse($hRequest)
		If @error Then
			$Return[1] = "Connection refused 5"
			Return $Return
		EndIf
		$headers = _WinHttpQueryHeaders($hRequest)
		$Received_Content = _WinHttpReadData($hRequest, 2, $Wi_ReadBufferSize)
	EndIf
	$Return[0] = $Received_Content
	$Return[1] = $headers
	Return $Return
EndFunc   ;==>_https

Func _http_fast($Domain_Name, $URI = "", $content = "", $http_cookies = "", $Additional_Header = "")
	Local $HTTP_Header, $Received_Content, $Content_Length, $Received_Piece, $Previous_Size, $Content_Type = "application/x-www-form-urlencoded"
	$init = TimerInit()
	If StringInStr($Domain_Name, ":") Then
		$Domain_Name = StringSplit($Domain_Name, ":")
		$Port = $Domain_Name[2]
		$Domain_Name = $Domain_Name[1]
	Else
		$Port = 80
	EndIf
	$Domain_Name = TCPNameToIP($Domain_Name)
	Local $retr = ""

	If $content <> "" Then
		$sock = TCPConnect($Domain_Name, $Port)
		If @error Then Return 0

		$sHeader = "Content-Type: application/x-www-form-urlencoded; charset=UTF-8" & @CRLF & _
				"Host: " & $Domain_Name & @CRLF & _
				"Content-Length: " & BinaryLen($content) & @CRLF & _
				$Additional_Header
		TCPSend($sock, "POST " & $URI & " HTTP/1.1" & @CRLF & _
				$sHeader & @CRLF & _
				$content)
	Else
		$sock = TCPConnect($Domain_Name, $Port)
		If @error Then Return 0

		$sHeader = "Host: " & $Domain_Name & @CRLF & _
				$Additional_Header
		TCPSend($sock, "GET " & $URI & " HTTP/1.1" & @CRLF & _
				$sHeader & @CRLF)
	EndIf
	Return 1
EndFunc   ;==>_http_fast

Func _http_header_disassemble($HTTP_Header)
	$HTTP_Header = StringSplit($HTTP_Header, @CRLF, 1)
	Dim $lines[$HTTP_Header[0] - 1][2]
	$lines[0][0] = $HTTP_Header[0] - 2
	For $i = 1 To $lines[0][0]
		$Delimiter = StringInStr($HTTP_Header[$i], " ")
		If $Delimiter Then
			$lines[$i][0] = StringLeft($HTTP_Header[$i], $Delimiter - 1)
			$lines[$i][1] = StringMid($HTTP_Header[$i], $Delimiter + 1)
		EndIf
	Next
	Return $lines
EndFunc   ;==>_http_header_disassemble

Func _http_header_getvalue(ByRef $HTTP_Header, $Variable_Name)
	For $i = 1 To $HTTP_Header[0][0]
		If $HTTP_Header[$i][0] = $Variable_Name Then Return $HTTP_Header[$i][1]
	Next
	Return -1
EndFunc   ;==>_http_header_getvalue

Func _BOM_Remove(ByRef $string)
	If StringLeft($string, 3) = BinaryToString(0xBFBBEF) Then $string = StringTrimLeft($string, 3)
	Return $string
EndFunc   ;==>_BOM_Remove

Func _httpSetReadBufferSize($size = 262144)
	$Wi_ReadBufferSize = $size
EndFunc

Func stringextract($string, $start, $end, $offset = 1)
	If $start = "" Then
		$left_bound = $offset
	Else
		$left_bound = StringInStr($string, $start, 0, 1, $offset)
	EndIf
	If $left_bound = 0 Then Return SetExtended(-1, "")
	$left_bound += StringLen($start)
	If $end = "" Then
		$right_bound = StringLen($string) + 1
	Else
		$right_bound = StringInStr($string, $end, 0, 1, $left_bound)
	EndIf
	If $right_bound = 0 Then Return SetExtended(-1, "")
	Return SetExtended($left_bound, StringMid($string, $left_bound, $right_bound - $left_bound))
EndFunc   ;==>stringextract

Func stringextractall($string, $start, $end)
	$offset = 1
	Dim $results[0]
	While 1
		$str = stringextract($string, $start, $end, $offset)
		If @extended = -1 Then Return $results
		$offset = @extended + StringLen($str) + StringLen($end)
		ReDim $results[UBound($results) + 1]
		$results[UBound($results) - 1] = $str
	WEnd
EndFunc   ;==>stringextractall


Func binaryappend($bin_a, $bin_b)
	$len = BinaryLen($bin_a) + BinaryLen($bin_b)
	$offset = BinaryLen($bin_a)
	$ptr = $offset + 1
	$struct = DllStructCreate("byte[" & $len & "]")
	DllStructSetData($struct, 1, $bin_a)
	While $ptr <= $len
		DllStructSetData($struct, 1, BinaryMid($bin_b, $ptr - $offset, 1), $ptr)
		$ptr += 1
	WEnd
	$bin_c = DllStructGetData($struct, 1)
	$struct = 0
	Return $bin_c
EndFunc   ;==>binaryappend