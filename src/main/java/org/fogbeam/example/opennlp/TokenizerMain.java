
package org.fogbeam.example.opennlp;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;


public class TokenizerMain {
	// Logger para esta clase
	private static final Logger logger = Logger.getLogger(TokenizerMain.class.getName());
	public static void main(String[] args) {
		if (args.length < 2) {
			logger.severe("Uso: java TokenizerMain <output_file> <input_file1> <input_file2> ...");
			return;
		}

		// Archivo de salida
		String outputFilePath = args[0];

		// Archivos de entrada
		List<String> inputFiles = new ArrayList<>();
		for (int i = 1; i < args.length; i++) {
			inputFiles.add(args[i]);
		}

		// Modelo de tokenización
		try (InputStream modelIn = new FileInputStream("models/en-token.model");
			 BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath, StandardCharsets.UTF_8))) {

			TokenizerModel model = new TokenizerModel(modelIn);
			Tokenizer tokenizer = new TokenizerME(model);

			// Procesar cada archivo
			for (int i = 1; i < args.length; i++) {
				String inputFilePath = args[i];
				logger.info("Procesando archivo: " + inputFilePath);

				try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath, StandardCharsets.UTF_8))) {
					String line;
					while ((line = reader.readLine()) != null) {
						// Tokenizar línea
						String[] tokens = tokenizer.tokenize(line);

						// Escribir cada token en una nueva línea
						for (String token : tokens) {
							writer.write(token);
							writer.newLine();
						}
					}
				} catch (IOException e) {
					logger.log(Level.SEVERE, "Error leyendo archivo: " + inputFilePath, e);
				}
			}

			logger.info("Procesamiento completo. Tokens guardados en: " + outputFilePath);

		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error al cargar el modelo o escribir el archivo de salida.", e);
		}
	}
}