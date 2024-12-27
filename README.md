# Junioraufgabe 1 - Wundertüte

Dokumentation und Programmcode von Simon Reilich

## Lösungsidee

Alle Spiele werden, gruppiert nach ihrer Art, in eine Queue gesteckt. Man geht dann die einzelnen Wundertüten durch (ist man bei der letzten angelangt, beginnt man wieder von vorne), nimmt immer ein Spiel aus der Queue und steckt es in die Tüte, solange, bis alle Spiele verteilt sind.

## Umsetzung

Die Lösungs-Idee wird in Java implementiert, der Name der Datei mit der Eingabe wird dem Programm beim Start im Terminal übergeben, sollte die Eingabe fehlerhaft sein, wird eine `IllegalArgumentException` geworfen und das Programm bricht ab.

Nachdem getestet wurde, ob das nötige Argument übergeben wurde, wird die `readFile()`-Methode aufgerufen, ihr wird auch der Dateiname (= `args[0]`) mitgegeben.

```java
public static ArrayList<Integer> readFile(String filename) throws IOException {
  ArrayList<Integer> read = new ArrayList<>();
  Pattern pattern = Pattern.compile(".+\\.txt");
  Matcher matcher = pattern.matcher(filename);

  if (!matcher.find()) {
	  throw new IllegalArgumentException("Wrong Argument: .txt file needed");
  }

  // Einlesen der übergebenen Datei
  String line;
  try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
    line = br.readLine();

    while (line != null) {
		  try {
        read.add(Integer.parseInt(line));
      } catch (Exception e) {
        throw new IllegalArgumentException("Wrong format: Only ints allowed");
      }
        line = br.readLine();
      }
    }
    return read;
  }
}
```

Zuerst wird eine Liste angelegt, in der die einzelnen Zeilen der Angabe gespeichert werden. Anschließend wird mithilfe einer Regular Expression geprüft, ob es sich bei der angegebenen Datei um eine .txt handelt. Nun beginnt das einlesen der Datei. Mittels eines `BufferedReader` wird sie Zeile für Zeile eingelesen, in einen Integer umgewandelt und in `read` gespeichert. Zum Schluss wird die Liste zurückgegeben.

```java
ArrayList<Integer> data = readFile(args[0]);
int numberOfBags = data.get(0);
int diferentGames = data.get(1);
int[] numOfGames = new int[diferentGames];
for (int i = 2; i < 2 + diferentGames; i++) {
  numOfGames[i - 2] = data.get(i);
}
```

Der Übersichtlichkeit halber werden die Einträge in der Liste noch in einzelne Variablen übertragen, dies ist jedoch nicht zwingend notwendig.

```java
public class SurpriseBag {

  private int[] games;

  public SurpriseBag(int size) {
    games = new int[size];
  }

  public void addGame(int game) {
    games[game]++;
  }
		
	@Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < games.length; i++) {
      sb.append("    Game ").append(i).append(": ")
					.append(games[i]).append(" times \n");
    }
    return sb.toString();
  }
}
```

Als nächstes wird ein Array von Objekten der Klasse `SurpriseBag` angelegt (die Länge beträgt `numberOfBags`). Diese Klasse repräsentiert die einzelnen Wundertüten. Im Array `games` wird die Anzahl an Spielen pro Spielesorte gespeichert, der Konstruktor der Klasse nimmt ein Argument für die Länge dieses Arrays entgegen (in unserem Fall wird hier immer `differentGames` übergeben). Die Methode `addGame()` erhält als Argument die ID der Spielesorte, welche der Tüte hinzugefügt werden soll und speichert diese Änderung in `games`. Zuletzt nun zur Methode `toString()`: Diese überschreibt die gleichnamige Methode der Klasse `Object`, wird also immer dann implizit aufgerufen, wenn ein Objekt der Klasse an eine Methode übergeben wird, die eigentlich einen `String` erwartet, sie wandelt also ein Objekt in seine Darstellung als `String` um.

```java
int currentBag = -1;
for (int i = 0; i < diferentGames; i++) {
	for (int j = 0; j < numOfGames[i]; j++) {
		currentBag++;
		currentBag %= numberOfBags;
		bags[currentBag].addGame(i);
	}
}
```

## Beispiele

Beispiel 1:

<aside>
➡️ Eingabe:   3 - 3 - 4 - 4 - 2

</aside>

<aside>
📤 Ausgabe

Surprisebag 1: 
    Game 0: 2 times 
    Game 1: 1 times 
    Game 2: 1 times 

Surprisebag 2: 
    Game 0: 1 times 
    Game 1: 2 times 
    Game 2: 0 times 

Surprisebag 3: 
    Game 0: 1 times 
    Game 1: 1 times 
    Game 2: 1 times

</aside>

Beispiel 2:

<aside>
➡️ Eingabe:   6 - 3 - 18 - 6 - 12

</aside>

<aside>
📤 Ausgabe

Surprisebag 1: 
    Game 0: 3 times 
    Game 1: 1 times 
    Game 2: 2 times 

Surprisebag 2: 
    Game 0: 3 times 
    Game 1: 1 times 
    Game 2: 2 times 

Surprisebag 3: 
    Game 0: 3 times 
    Game 1: 1 times 
    Game 2: 2 times 

Surprisebag 4: 
    Game 0: 3 times 
    Game 1: 1 times 
    Game 2: 2 times 

Surprisebag 5: 
    Game 0: 3 times 
    Game 1: 1 times 
    Game 2: 2 times 

Surprisebag 6: 
    Game 0: 3 times 
    Game 1: 1 times 
    Game 2: 2 times

</aside>

Beispiel 3:

<aside>
➡️ Eingabe:   9 - 4 - 10 - 9 - 3 - 5

</aside>

<aside>
📤 Ausgabe

Surprisebag 1: 
    Game 0: 2 times 
    Game 1: 1 times 
    Game 2: 0 times 
    Game 3: 0 times 

Surprisebag 2: 
    Game 0: 1 times 
    Game 1: 1 times 
    Game 2: 1 times 
    Game 3: 0 times 

Surprisebag 3: 
    Game 0: 1 times 
    Game 1: 1 times 
    Game 2: 1 times 
    Game 3: 0 times 

Surprisebag 4: 
    Game 0: 1 times 
    Game 1: 1 times 
    Game 2: 1 times 
    Game 3: 0 times 

Surprisebag 5: 
    Game 0: 1 times 
    Game 1: 1 times 
    Game 2: 0 times 
    Game 3: 1 times 

Surprisebag 6: 
    Game 0: 1 times 
    Game 1: 1 times 
    Game 2: 0 times 
    Game 3: 1 times 

Surprisebag 7: 
    Game 0: 1 times 
    Game 1: 1 times 
    Game 2: 0 times 
    Game 3: 1 times 

Surprisebag 8: 
    Game 0: 1 times 
    Game 1: 1 times 
    Game 2: 0 times 
    Game 3: 1 times 

Surprisebag 9: 
    Game 0: 1 times 
    Game 1: 1 times 
    Game 2: 0 times 
    Game 3: 1 times

</aside>

Beispiel 4:

<aside>
➡️ Eingabe:   11 - 5 - 2 - 11 - 6 - 2 - 1

</aside>

<aside>
📤 Ausgabe

Surprisebag 1:
    Game 0: 1 times
    Game 1: 1 times
    Game 2: 0 times
    Game 3: 0 times
    Game 4: 0 times

Surprisebag 2:
    Game 0: 1 times
    Game 1: 1 times
    Game 2: 0 times
    Game 3: 0 times
    Game 4: 0 times

Surprisebag 3:
    Game 0: 0 times
    Game 1: 1 times
    Game 2: 1 times
    Game 3: 0 times
    Game 4: 0 times

Surprisebag 4:
    Game 0: 0 times
    Game 1: 1 times
    Game 2: 1 times
    Game 3: 0 times
    Game 4: 0 times

Surprisebag 5:
    Game 0: 0 times
    Game 1: 1 times
    Game 2: 1 times
    Game 3: 0 times
    Game 4: 0 times

Surprisebag 6:
    Game 0: 0 times
    Game 1: 1 times
    Game 2: 1 times
    Game 3: 0 times
    Game 4: 0 times

Surprisebag 7:
    Game 0: 0 times
    Game 1: 1 times
    Game 2: 1 times
    Game 3: 0 times
    Game 4: 0 times

Surprisebag 8:
    Game 0: 0 times
    Game 1: 1 times
    Game 2: 1 times
    Game 3: 0 times
    Game 4: 0 times

Surprisebag 9:
    Game 0: 0 times
    Game 1: 1 times
    Game 2: 0 times
    Game 3: 1 times
    Game 4: 0 times

Surprisebag 10:
    Game 0: 0 times
    Game 1: 1 times
    Game 2: 0 times
    Game 3: 1 times
    Game 4: 0 times

Surprisebag 11:
    Game 0: 0 times
    Game 1: 1 times
    Game 2: 0 times
    Game 3: 0 times
    Game 4: 1 times

</aside>

Beispiel 5: 

<aside>
➡️ Eingabe:   17 - 6 - 21 - 114 - 38 - 97 - 513 - 89

</aside>

<aside>
📤 Ausgabe

Surprisebag 1:
    Game 0: 2 times
    Game 1: 6 times
    Game 2: 3 times
    Game 3: 5 times
    Game 4: 31 times
    Game 5: 5 times

Surprisebag 2:
    Game 0: 2 times
    Game 1: 6 times
    Game 2: 3 times
    Game 3: 5 times
    Game 4: 30 times
    Game 5: 6 times

Surprisebag 3:
    Game 0: 2 times
    Game 1: 6 times
    Game 2: 3 times
    Game 3: 5 times
    Game 4: 30 times
    Game 5: 6 times

Surprisebag 4:
    Game 0: 2 times
    Game 1: 6 times
    Game 2: 2 times
    Game 3: 6 times
    Game 4: 30 times
    Game 5: 6 times

Surprisebag 7:
    Game 0: 1 times
    Game 1: 7 times
    Game 2: 2 times
    Game 3: 6 times
    Game 4: 30 times
    Game 5: 5 times

Surprisebag 8:
    Game 0: 1 times
    Game 1: 7 times
    Game 2: 2 times
    Game 3: 6 times
    Game 4: 30 times
    Game 5: 5 times

Surprisebag 9:
    Game 0: 1 times
    Game 1: 7 times
    Game 2: 2 times
    Game 3: 6 times
    Game 4: 30 times
    Game 5: 5 times

Surprisebag 10:
    Game 0: 1 times
    Game 1: 7 times
    Game 2: 2 times
    Game 3: 6 times
    Game 4: 30 times
    Game 5: 5 times

Surprisebag 13:
    Game 0: 1 times
    Game 1: 7 times
    Game 2: 2 times
    Game 3: 6 times
    Game 4: 30 times
    Game 5: 5 times

Surprisebag 14:
    Game 0: 1 times
    Game 1: 7 times
    Game 2: 2 times
    Game 3: 6 times
    Game 4: 30 times
    Game 5: 5 times

Surprisebag 15:
    Game 0: 1 times
    Game 1: 7 times
    Game 2: 2 times
    Game 3: 6 times
    Game 4: 30 times
    Game 5: 5 times

Surprisebag 16:
    Game 0: 1 times
    Game 1: 7 times
    Game 2: 2 times
    Game 3: 5 times
    Game 4: 31 times
    Game 5: 5 times

… (Ausgabe aus Gründen der Übersichtlichkeit gekürzt)

</aside>

Beispiel 6:

<aside>
➡️ Eingabe:   97 - 23 - 50 - 41 - 163 - 124 - 113 - 83 - 129 - 65 - 70 - 56 - 127 - 83 - 24 - 69 - 182 - 174 - 76 - 106 - 92 - 155 - 29 - 133 - 121

</aside>

<aside>
📤 Ausgabe

Surprisebag 1:
    Game 0: 1 times
    Game 1: 0 times
    Game 2: 2 times
    Game 3: 1 times
    Game 4: 2 times
    Game 5: 0 times
    Game 6: 2 times
    Game 7: 0 times
    Game 8: 1 times
    Game 9: 1 times
    Game 10: 1 times
    Game 11: 1 times
    Game 12: 0 times
    Game 13: 1 times
    Game 14: 2 times
    Game 15: 2 times
    Game 16: 0 times
    Game 17: 1 times
    Game 18: 1 times
    Game 19: 2 times
    Game 20: 0 times
    Game 21: 2 times
    Game 22: 1 times

Surprisebag 34:
    Game 0: 1 times
    Game 1: 0 times
    Game 2: 2 times
    Game 3: 1 times
    Game 4: 1 times
    Game 5: 1 times
    Game 6: 1 times
    Game 7: 1 times
    Game 8: 1 times
    Game 9: 0 times
    Game 10: 2 times
    Game 11: 1 times
    Game 12: 0 times
    Game 13: 0 times
    Game 14: 2 times
    Game 15: 2 times
    Game 16: 1 times
    Game 17: 1 times
    Game 18: 1 times
    Game 19: 2 times
    Game 20: 0 times
    Game 21: 1 times
    Game 22: 2 times

Surprisebag 67:
    Game 0: 0 times
    Game 1: 1 times
    Game 2: 1 times
    Game 3: 2 times
    Game 4: 1 times
    Game 5: 1 times
    Game 6: 1 times
    Game 7: 1 times
    Game 8: 0 times
    Game 9: 1 times
    Game 10: 1 times
    Game 11: 1 times
    Game 12: 0 times
    Game 13: 1 times
    Game 14: 2 times
    Game 15: 2 times
    Game 16: 1 times
    Game 17: 1 times
    Game 18: 1 times
    Game 19: 1 times
    Game 20: 1 times
    Game 21: 1 times
    Game 22: 1 times

… (Ausgabe aus Gründen der Übersichtlichkeit gekürzt)

</aside>
