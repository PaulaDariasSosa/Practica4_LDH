
package org.fogbeam.example.opennlp;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;


public class SentenceDetectionMain
{
	private static final Logger logger = Logger.getLogger(SentenceDetectionMain.class.getName());

	/**
	 * @brief Detect sentences in a demo data file
	 * @param args
	 * @throws Exception
	 */
	public static void main( String[] args ) throws Exception
	{
		InputStream modelIn = new FileInputStream( "models/en-sent.model" );
		InputStream demoDataIn = new FileInputStream( "demo_data/en-sent1.demo" );
		
		
		
		try
		{
			SentenceModel model = new SentenceModel( modelIn );
			SentenceDetectorME sentenceDetector = new SentenceDetectorME(model);
			
			String demoData = convertStreamToString( demoDataIn );
			
			String sentences[] = sentenceDetector.sentDetect( demoData );
			
			for( String sentence : sentences )
			{
				System.out.println( sentence + "\n" );
			}
			
			
			
		}
		catch( IOException e )
		{
			logger.log(Level.SEVERE, "Error", e);
		}
		finally
		{
			if( modelIn != null )
			{
				try
				{
					modelIn.close();
				}
				catch( IOException e )
				{
				}
			}
			
			
			if( demoDataIn != null )
			{
				try
				{
					demoDataIn.close();
				}
				catch( IOException e )
				{
				}
			}
			
			
		}
		
		
		System.out.println( "done" );
		
	}
	
	
	static String convertStreamToString(java.io.InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
	
}
