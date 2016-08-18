package plugin.raquel.examples.helloworld;

import java.util.regex.Pattern;

public class AlgExemplo {
	
	public static final String[] testeErro = {"objeto", ".objeto", "objeto.", "objeto;",
			"objeto.function", "objeto.function;", ";objeto.function", "objeto.;function", "objeto;.function",
			"objeto()", ".objeto()", "objeto.()", "objeto().",
			"objeto().function", "objeto.()function","()objeto.function",
			"objeto.function()","objeto.function().", 
			"a.somadiferente().subdiferente().multdiferente().raizdiferente()",
			"objeto.function().function2", "objeto.function().function2.", "objeto.function().function2.;"};
	
	public static final String[] testeValido = {"objeto.function().function2();",
			"objeto.function().function2().function3();",
			"objeto.function().function2().function3().function4();", 
			"objeto.function().function2().function3().function4().function5();",
			"a.somadiferente().subdiferente().multdiferente().raizdiferente();"};
	
	public static void splitMessageChain (String s) {
		// retira o ";" do final da string		
		s = s.replace(";", " ");
		
		// Quebra a variável quando acha . e armazena a sobra numa posição do array aux
		// a().b() -> . é descartando e a() fica em aux[0] e b() em aux[1]
		String[] aux = s.split(Pattern.quote("."));		

		// Pega o tamanho da string aux
		// Imprime a variável aux na tela
		System.out.println("Objeto: " + aux[0]);		
		for (int i = 1; i < aux.length; i++) {
			System.out.println("Método[" + i + "]: " + aux[i]);
		}		
	}
	
	public static void verificaMessageChain (String s) {		
		if (s!=null && s.matches("[\\w]+([\\.]+[\\w]+[(]+[)]){2,}+[;]")) {
			System.out.println("\nÉ Message Chain para "+s+"\n");
			splitMessageChain(s);
		} else {
			System.out.println("\nNão é Message Chain para "+s+"\n");	
		}
	}
	
	public static void testaStrings (String[] s) {
		for (int i = 0; i<s.length; i++) {
			verificaMessageChain(s[i]);
		}
	}


	public static void main(String[] args) {
		testaStrings(testeErro);		
		System.out.println("\n#####################################################\n");		
		testaStrings(testeValido);
	}
}