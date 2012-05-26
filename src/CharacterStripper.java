
public class CharacterStripper {
	public static void main(String[] args) {
		String testString = "120938H>?<E)(*#l)(*L192L731O ````W=-0O23423R2093820342L?><?>????D";
		String pattern = "(?i)[^A-Z]";

		String strippedString = testString.replaceAll(pattern, "");
		System.out.println("Original String is:         " + testString);
		System.out.println("After Replacing Characters: " + strippedString);
	}

}
