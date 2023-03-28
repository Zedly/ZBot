/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zedly.zbot.parsers;

import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.apache.commons.lang3.tuple.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Dennis
 */
public class MaterialReportParser {

    private static final ArrayList<Pair<BlockDataDescriptor, ArrayList<String>>> ALL_TYPES = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        TreeMap<BlockDataDescriptor, ArrayList<String>> typeTreeSet = new TreeMap<>();
        Reader reader = new FileReader(new File("blocks.json"));
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(reader);

        for (Object materialName : json.keySet()) {
            JSONObject materialObject = (JSONObject) json.get(materialName);
            String shortMatName = ((String) materialName).substring(10);
            if (materialObject.containsKey("properties")) {
                JSONObject propertiesObject = (JSONObject) materialObject.get("properties");
                Collection<String> propertyNames = propertiesObject.keySet().stream().map((p) -> (String) p).toList();
                BlockDataDescriptor bdd = new BlockDataDescriptor("", propertyNames);

                for (String property : propertyNames) {
                    PropertyDescriptor pd = PropertyDescriptor.forName(property);
                    JSONArray propertyValues = (JSONArray) propertiesObject.get(property);
                    for (Object q : propertyValues) {
                        pd.rememberValue((String) q);
                    }
                }
                ArrayList<String> materialsForThisType = typeTreeSet.getOrDefault(bdd, new ArrayList<>());
                materialsForThisType.add(shortMatName);
                typeTreeSet.put(bdd, materialsForThisType);
            }
        }

        for (Entry<BlockDataDescriptor, ArrayList<String>> entry : typeTreeSet.entrySet()) {
            ALL_TYPES.add(Pair.of(entry.getKey(), entry.getValue()));
        }

        for (int i = 0; i < ALL_TYPES.size(); i++) {
            BlockDataDescriptor a = ALL_TYPES.get(i).getKey();
            for (int j = i + 1; j < ALL_TYPES.size(); j++) {
                BlockDataDescriptor b = ALL_TYPES.get(j).getKey();
                a.rememberIfDirectlyExtends(b);
            }
        }

        Reader inputReader = new InputStreamReader(System.in);
        Scanner sc = new Scanner(inputReader);
        for (int i = ALL_TYPES.size(); i-- > 0;) {
            BlockDataDescriptor pd = ALL_TYPES.get(i).getKey();
            ArrayList<String> mats = ALL_TYPES.get(i).getValue();
            System.out.println(pd.toString() + "  used in " + mats);
            //String name = sc.nextLine();
            String name = generateName();
            pd.name = name;
        }

        for (PropertyDescriptor pd : PropertyDescriptor.values()) {

            //System.out.println(pd.className() + " " + pd.memberName() + ";");
            if (!pd.isPrimitive()) {
                System.out.print(pd.getEnumBody());
            }

        }

        for (BlockDataDescriptor bdd : typeTreeSet.descendingKeySet()) {
            System.out.print(bdd.getInterfaceBody());
            System.out.println("");
            System.out.print(bdd.getImplBody());
            System.out.println("");
        }

        // for all known property descriptors:
        // generate the enum file
        // for all block data descriptors:
        // generate the interface:
        // thisname extends [parents]
        // for each declared property:
        // if boolean:
        // public boolean is<propertyname>();
        // elseif int:
        // public int get<propertyname();
        // else:
        // public <propertyname> get<propertyname>();
        // generate the impl class:
        // generate the interface:
        // thisname implements [interface], [parents]
        // for EVERY property:
        // private final <propertyname> <propertyname.tolower>;
        // <propertyname> <propertyname.tolower>;
        //
        // public <thisname>( /* for EVERY property: <propertyname> <propertyname.tolower>, */ ) {
        // for EVERY property:
        //   this.<propertyname.tolower> = <propertyname.tolower>;
        // }
        // 
        // for EVERY property:
        // if boolean:
        // public boolean is<propertyname>() {
        //   return <propertyname.tolower>;}
        // elseif int:
        // public int get<propertyname>() { return <propertyname.tolower>();
        // else:
        // public <propertyname> get<propertyname>() return <propertyname.tolower>();
    }

    static Random rnd = new Random();

    public static String generateName() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append((char) (rnd.nextInt(26) + 0x41));
        }
        return sb.toString();
    }

    // for each BlockDataDescriptor: ask how we want to name it, ask if we want to extend any other one
    // for each material m:
    // set of value names defines a BlockDataDescriptor
    // now we know what it's called
    // big map.add( int id, material mat
    public static class MaterialDescriptor {

        // Material
        // List<PropertyDescriptor>
    }

    public static class PropertyDescriptor {

        private enum Type {
            BOOLEAN,
            INT,
            ENUM,
            UNKNOWN,;
        }

        private static final HashMap<String, PropertyDescriptor> INSTANCES = new HashMap<>();
        private final String name;
        private final TreeSet<String> values = new TreeSet<>();
        private Type type = Type.UNKNOWN;

        public static PropertyDescriptor forName(String name) {
            return INSTANCES.computeIfAbsent(name, (n) -> new PropertyDescriptor(n));
        }

        public static Collection<PropertyDescriptor> values() {
            return INSTANCES.values();
        }

        private static String snakeToCamel(String snakeCase) {
            StringBuilder camelCase = new StringBuilder();

            // Split the string by underscores
            String[] words = snakeCase.split("_");

            // Iterate over the words and capitalize the first letter of each word
            for (String word : words) {
                camelCase.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1));
            }

            return camelCase.toString();
        }

        public PropertyDescriptor(String name) {
            this.name = snakeToCamel(name);
        }

        public void rememberValue(String value) {
            values.add(value);
            type = Type.UNKNOWN;
        }

        public boolean isBoolean() {
            isPrimitive();
            return type == Type.BOOLEAN;
        }

        public boolean isPrimitive() {
            switch (type) {
                case BOOLEAN:
                    return true;
                case INT:
                    return true;
                case ENUM:
                    return false;
                default:
                    boolean isInt = true;
                    boolean isBoolean = true;
                    for (String v : values) {
                        if (!v.equals("true") && !v.equals("false")) {
                            isBoolean = false;
                        }
                        if (!v.matches("\\d+")) {
                            isInt = false;
                        }
                    }
                    if (isBoolean) {
                        type = Type.BOOLEAN;
                    } else if (isInt) {
                        type = Type.INT;
                    } else {
                        type = Type.ENUM;
                    }
                    return isPrimitive();
            }
        }

        public int compareTo(PropertyDescriptor b) {
            return this.name.compareTo(b.name);
        }

        public String getEnumPath() {
            return "zbotapi/zedly/zbot/block/property/" + name + ".java";
        }

        public String getEnumBody() {
            StringBuilder sb = new StringBuilder();

            sb.append("package zedly.zbot.block.property;\n");
            sb.append("\n");
            sb.append("public enum").append(name).append(" {\n");
            sb.append("\n");
            for (String v : values) {
                sb.append("    ").append(v.toUpperCase()).append(",\n");
            }
            sb.append(";\n");
            sb.append("}\n");
            return sb.toString();
        }

        public String className() {
            switch (type) {
                case INT:
                    return "int";
                case BOOLEAN:
                    return "boolean";
                case ENUM:
                    return name;
                default:
                    isPrimitive();
                    return className();
            }
        }

        public String memberName() {
            return Character.toLowerCase(name.charAt(0)) + name.substring(1);
        }
    }

    public static class BlockDataDescriptor implements Comparable<BlockDataDescriptor> {

        private static final ArrayList<BlockDataDescriptor> BASES = new ArrayList<>();

        private String name;
        private ArrayList<BlockDataDescriptor> parents = new ArrayList<>();
        private ArrayList<String> allSymbols = new ArrayList<>();

        public BlockDataDescriptor(String name, String... properties) {
            this.name = name;
            for (String p : properties) {
                allSymbols.add(p);
            }
        }

        public BlockDataDescriptor(String name, Collection<String> properties) {
            this.name = name;
            this.allSymbols.addAll(properties);
        }

        public ArrayList<String> getDeclaredSymbols() {
            ArrayList<String> declared = new ArrayList<>();
            for (String symb : allSymbols) {
                boolean propIsInherited = false;
                for (BlockDataDescriptor parent : parents) {
                    if (parent.allSymbols.contains(symb)) {
                        propIsInherited = true;
                    }
                }
                if (propIsInherited) {
                    continue;
                }
                declared.add(symb);
            }
            return declared;
        }

        public boolean hasSymbol(String symbol) {
            return inheritsSymbol(symbol) || declaresSymbol(symbol);
        }

        public boolean declaresSymbol(String symbol) {
            for (String s : allSymbols) {
                if (symbol.equals(s)) {
                    return true;
                }
            }
            return false;
        }

        public boolean inheritsSymbol(String symbol) {
            for (BlockDataDescriptor parent : parents) {
                if (parent.hasSymbol(symbol)) {
                    return true;
                }
            }
            return false;
        }

        public boolean doesExtend(BlockDataDescriptor p) {
            for (String s : p.allSymbols) {
                if (!allSymbols.contains(s)) {
                    return false;
                }
            }
            return true;
        }

        public void rememberIfDirectlyExtends(BlockDataDescriptor b) {
            if (doesExtend(b)) {
                for (BlockDataDescriptor parent : parents) {
                    if (parent.doesExtend(b)) {
                        return;
                    }
                }
                parents.add(b);
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            for (BlockDataDescriptor parent : parents) {
                sb.append(parent.name).append(",");
            }
            sb.append("},  [");
            for (String prop : allSymbols) {
                boolean propIsInherited = false;
                for (BlockDataDescriptor parent : parents) {
                    if (parent.allSymbols.contains(prop)) {
                        propIsInherited = true;
                    }
                }
                if (propIsInherited) {
                    continue;
                }
                sb.append(prop);
                sb.append(",");
            }
            sb.append("]");
            return sb.toString();
        }

        // String name
        // type: {boolean, int, enum}
        // String value
        // aggregate values
        // get argument string for value
        // is class
        // get enum name
        // get enum body
        static {
            BASES.add(new BlockDataDescriptor("Waterloggable", "waterlogged"));
        }

        @Override
        public int compareTo(BlockDataDescriptor p) {
            if (this.allSymbols.size() < p.allSymbols.size()) {
                return 1;
            } else if (this.allSymbols.size() > p.allSymbols.size()) {
                return -1;
            }

            for (int i = 0; i < allSymbols.size(); i++) {
                int c = allSymbols.get(i).compareTo(p.allSymbols.get(i));
                if (c != 0) {
                    return c;
                }
            }
            return 0;
        }

        public boolean equals(BlockDataDescriptor p) {
            return compareTo(p) == 0;
        }
        
        public String getInterfacePath() {
            return "zbotapi/zedly/zbot/block/data/Craft" + name + ".java";
        }

        public String getInterfaceBody() {
            StringBuilder sb = new StringBuilder();

            sb.append("package zedly.zbot.block.property;\n");
            sb.append("\n");
            sb.append("public interface ").append(name);
            if (!parents.isEmpty()) {
                sb.append(" extends ");
                sb.append(parents.get(0).name);
                for (int i = 1; i < parents.size(); i++) {
                    sb.append(", ").append(parents.get(i).name);
                }
            }
            sb.append(" {\n");
            sb.append("\n");
            ArrayList<String> declaredSymbols = getDeclaredSymbols();
            for (int i = 0; i < declaredSymbols.size(); i++) {
                PropertyDescriptor pd = PropertyDescriptor.forName(declaredSymbols.get(i));
                sb.append("    public ").append(pd.className()).append(" ").append(pd.isBoolean() ? "is" : "get").append(pd.name).append("();\n");
                sb.append("\n");
            }
            sb.append("}\n");
            return sb.toString();
        }

        public String getImplPath() {
            return "zbotapi/zedly/zbot/block/data/Craft" + name + ".java";
        }

        public String getImplBody() {
            StringBuilder sb = new StringBuilder();

            sb.append("package zedly.zbot.block.property;\n");
            sb.append("\n");
            sb.append("public class Craft").append(name).append(" implements ").append(name).append(" {\n");
            sb.append("\n");
            for (String s : allSymbols) {
                PropertyDescriptor pd = PropertyDescriptor.forName(s);
                sb.append("    private final ").append(pd.className()).append(" ").append(pd.memberName()).append(";\n");
            }
            sb.append("\n");
            sb.append("    public ").append(name).append("(final ");
            PropertyDescriptor pd = PropertyDescriptor.forName(allSymbols.get(0));
            sb.append(pd.className()).append(" ").append(pd.memberName());
            for (int i = 1; i < allSymbols.size(); i++) {
                pd = PropertyDescriptor.forName(allSymbols.get(i));
                sb.append(", final ").append(pd.className()).append(" ").append(pd.memberName());
            }
            sb.append(") {\n");
            for (int i = 0; i < allSymbols.size(); i++) {
                pd = PropertyDescriptor.forName(allSymbols.get(i));
                sb.append("        this.").append(pd.memberName()).append(" = ").append(pd.memberName()).append(";\n");
            }
            sb.append("    }\n");
            sb.append("\n");
            for (int i = 0; i < allSymbols.size(); i++) {
                pd = PropertyDescriptor.forName(allSymbols.get(i));
                sb.append("    @Override\n");
                sb.append("    public ").append(pd.className()).append(" ").append(pd.isBoolean() ? "is" : "get").append(pd.name).append("() {\n");
                sb.append("        return ").append(pd.memberName()).append(";\n");
                sb.append("    }\n");
                sb.append("\n");
            }
            sb.append("}\n");
            return sb.toString();
        }
    }
}
