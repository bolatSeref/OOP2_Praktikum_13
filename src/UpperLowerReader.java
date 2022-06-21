import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilterReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

/** 1a diese Klasse wird von FilterReader abgeleitet
 *  Filter in einem Lese-Datenstrom */
public class UpperLowerReader extends FilterReader{

	/**  1a Konstruktor
	 *   @param in Vorgänger im Datenstrom */
	protected UpperLowerReader(Reader in) {
		super(in);   //init Vorgänger
	}
	
	/** 1b verwandelt die Großbuchstaben in Kleinbuchstaben und umgekehrt.
	 *  @param c entspricht der kanonische Implementierung ein int wert
	 *  @return den Wert des umwandeltes Characters als ASCII  */
	public int umwandelteCharacter (int c) {
		
		//vorübergehende Variable
		char tempChar = ' ';
			
		//Wenn der Character groß/Upper ist
		if (Character.isUpperCase(c)) {
			tempChar = (char) Character.toLowerCase(c);
				
		//Ist der Character klein/Lower
			} else {
				tempChar = (char) Character.toUpperCase(c);
			}
		
		//casting 		
		return (int) tempChar;
		
	}
	
	/** 1c liest ein Zeichen aus dem Datenstrom  
	 *  @return gibt durch Rückgabe im Datenstrom weiter
	 *  @throws IOException */
	public int read () throws IOException {		
		
		try {
			//ein Zeichen vom Vorgänger im Datenstrom lesen
			int i = in.read();
			
			//Zeichen modifizieren
			if ( i != -1) return umwandelteCharacter(i); 
				//End of Stream
				else 	  return -1;
			
		} catch (IOException e) {
			System.err.println("IO Exception ist aufgetreten..."+ e);
			//weiterwerfen
			throw e;
		}	
		
	}

	
	/** 1d liest aus dem Datenstrom mit den Prametern angegebene Anzahl von Zeichen */
	public int read (char [] buf, int off, int len) throws IOException {
		
		char [] localbuf = new char [len];
		
		//Rückgabe
		int anzahl;
		try {
			//zum Lesen der zu modifizierten Zeichen vom Vorgänger im Datenstrom
			//wird die Methode mit der gleichen Signatur
			anzahl = in.read(localbuf, 0, len);
			
			if (anzahl ==-1) return -1;
			
			else {
				  for (int i=0; i < anzahl; i++) {
				  //mit der oben definierten Methode umwandelt	  
			      buf[i+off] = (char) umwandelteCharacter(localbuf[i]);
				  }
				  return anzahl;
			}		
			
		} catch (IOException e) {
			
		System.err.println("Bei der Umwandlung ist ein Fehler auftreten...");
		//weiterwerfen
		throw e;			
		}	
	}
	
	/** 2b 
	 *  
	 *  @param args Arguments 
	 *  @throws IOException 
	 */
	public static void main (String [] args) throws IOException {
		
		//Die Variablen
		UpperLowerReader upLowReader = null;
		UpperLowerReader upLowReader2 = null; 
		
		try {
		//2c Zum Testen ein Datenstrom aufbauen, der aus StringReader liest.
		upLowReader = new UpperLowerReader (new StringReader("#OOP2@Praktikum~"));
		
		//2d
		//Ein neuer Datenstrom wird erzeugt
		upLowReader2 = new UpperLowerReader(new StringReader("#1:-Javaisteine*"
												+ "objektorientierte*Programmiersprache"));
		
			
			// 2c Testen
			//System.out.println((char)upLowReader.read());
			int g  = 0; // das gelesene Zeichen ist ein int
			char c = 0; // umgewandelt in char
			
			//in einer while Schleife
			while(g != -1) {
				g = upLowReader.read();
				//casting
				c = (char) g;
				if (g != -1) System.out.println("Jedes Zeichen: " + c);
			}
						
		
		//2d durch wiederholten Aufruf der read (buff, off, len) Methode
		//werden alle Zeichen im Datenstrom gelesen
		int anz = 0;  //anzahl der chars, die ins Array geschrieben wurden
		String ausgabe;
		int off = 0; //index, ab dem in das Array geschrieben wurden
		int len = 4; //max anzahl der chars, die ins Array geschrieben wurden
		
		char buf [] = new char [len + off];
		
		while (anz != -1) { //solange nicht end of stream
			anz = upLowReader2.read(buf, off, len);
			if (anz != -1) {
				ausgabe = new String(buf, off, anz);
				System.out.print(ausgabe);
			} else {
				System.out.println("\nEnd of Stream");
			}
		}
		
		
		//2e Ergänzen close() Methode und dann nochmals von ihm ein Zeichen lesen
		upLowReader2.close();		
		upLowReader2.read();
		/*  2e Stream wurde geschlossen...
		IO Exception ist aufgetreten...java.io.IOException: Stream closed
		Beim IO ist etwas shiefgegangen:
		 Stream closed
		Exception in thread "main" java.io.IOException: Stream closed
			at java.base/java.io.StringReader.ensureOpen(StringReader.java:57)
			at java.base/java.io.StringReader.read(StringReader.java:70)
			at UpperLowerReader.read(UpperLowerReader.java:46)
			at UpperLowerReader.main(UpperLowerReader.java:142)
		*/
			
		} catch (IOException e) {
			System.err.println("Beim IO ist etwas shiefgegangen:\n " + e.getMessage());
			throw e; //weiterwerfen
		}		
		
		//Variable erzeugen
		UpperLowerReader upLowReader3;
		
		System.out.println("Aufgabe 3C");
		try {
		
		//3b Quell-File ist 
			upLowReader3 = new UpperLowerReader (new FileReader(
					    "hallo.txt"));
		
		//System.out.println((char)upLowReader3.read()); //gibt @ zurück korrekt
		
		//3d eine Ausnahme provozieren mit falschem Pfad
		//upLowReader3 = new UpperLowerReader (
		//new FileReader("C:\\Users\\famhe\\eclipse-workspace\\P12-IOFilterWriter\\heinz.txt"));	
		
		//3c in einer Schleife testen
		int h  = 0; // das gelesene Zeichen ist ein int
		char ch = 0; // umgewandelt in char
		
		//in einer while Schleife
		while(h != -1) {
			h = upLowReader3.read();
			ch = (char) h;
			if (h != -1) System.out.print(ch);
		
		}
		//Schließen den Datenstrom durch den Aufruf der close () Methode...
		upLowReader3.close();
		} catch (IOException e) {
			System.err.println("Beim IO ist etwas shiefgegangen:\n " + e.getMessage());
			throw e; //weiterwerfen
		}
		
		//3d
		//Exception in thread "main" java.io.FileNotFoundException: C:\Users\famhe\eclipse-workspace\P12-IOFilterWriter\heinz.txt 
		//(Das System kann die angegebene Datei nicht finden)
		
		//4b eine Variable für den Datenstromm		
		BufferedReader bufread;
		
		try {
			bufread = new BufferedReader(new UpperLowerReader(new FileReader("hallo.txt")));
			//4c alle Zeichen in dem Datenstrom werden mit der Methode readLine() gelesen...
			System.out.println("\nBufread in der hallo.txt Datei: " + bufread.readLine());
			bufread.close();
		} catch (IOException e) {
			System.err.println("Beim IO ist etwas shiefgegangen:\n " + e.getMessage());
			throw e; //weiterwerfen
		}
		
		//4c Wäre BufferedReader zwischen UpperLowerReader und FileReader gewesen,
		//hätte aus Performance Gründen besser/leistungsfähig/mit wenigerem Aufwand funktioniert...
		//Er sammelt alle Zeichen in einem Puffer Speicher bzw. Buffer
		//Für den Vorgänger im Datenstrom wird deutlich seltener read() aufgerufen
		//Es werden aber mehr Zeichen bei jedem Aufruf von read() gelesen

	} //Ende der Main Methode
	
	
	
	
	

} 
