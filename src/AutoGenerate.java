import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class AutoGenerate {

	public static void main(String[] args) throws IOException {
		int i = 0;
		
		File sourceFiles = new File("source");
		File[] listOfSourceFiles = sourceFiles.listFiles();
		
		for (File file : listOfSourceFiles) {
		    if (file.isFile()) {
				
		    	List<String> fileContent = Files.readAllLines(Paths.get("source/"+file.getName()));
				
				Path resultFilePath = Files.createFile(Paths.get("result/"+snakeToCamel(file.getName().substring(0, file.getName().length()-4))+".tsx"));
				File resultFile = new File(resultFilePath.toString());
				FileWriter writer = new FileWriter(resultFile);

				writer.write("import React, { Component } from 'react';\n");
				writer.write("export default class "+snakeToCamel(file.getName().substring(0, file.getName().length()-4))+" extends Component {"+"\n");
				writer.write("    render() {\n");
				writer.write("        return (\n");
				writer.write("            "+fileContent.get(0)+"\n");
				writer.write("        )\n");
				writer.write("    }\n");
				writer.write("}");
				writer.flush();
				writer.close();
				
				i++;
		    }
		}
		System.out.println("Converted File Length: "+i);
	}
	
	public static String snakeToCamel(String str) {
        str = str.substring(0, 1).toUpperCase() + str.substring(1);
        StringBuilder builder = new StringBuilder(str);
        for (int i = 0; i < builder.length(); i++) {
            if (builder.charAt(i) == '-') {
                builder.deleteCharAt(i);
                builder.replace( i, i + 1, String.valueOf(Character.toUpperCase(builder.charAt(i))));
            }
        }
        return builder.toString();
    }
}