package pescador;

public enum Parceiro {
	LIVELO("livelo", "https://www.pontofrio-imagens.com.br/HotSite/", "B2B_Livelo",4,10),
	ESFERA("Esfera", "https://www.casasbahia-imagens.com.br/HotSite/", "B2B_Esfera",4,10), 
	AZUL("tudoazul", "https://www.extra-imagens.com.br/HotSite/", "B2B_TudoAzul",8,25);


	private String descricao;
	private String dominio;
	private String sigla;
	private int minPts;
	private int maxPts;

	private Parceiro(String descricao, String dominio, String sigla,int minPts,int maxPts) {
		this.descricao = descricao;
		this.dominio = dominio;
		this.sigla = sigla;
		this.minPts = minPts;
		this.maxPts = maxPts;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getDominio() {
		return dominio;
	}

	public String getSigla() {
		return sigla;
	}
	
	public int getMaxPts() {
		return maxPts;
	}

	public int getMinPts() {
		return minPts;
	}


	public static Parceiro toEnum(String descricao) {
		if (descricao == null) {
			return null;
		}

		for (Parceiro x : Parceiro.values()) {
			if (descricao.equals(x.getDescricao())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Parceiro inválido: " + descricao);
	}
	
	public static Parceiro toEnumBySigla(String sigla) {
		if (sigla == null) {
			return null;
		}

		for (Parceiro x : Parceiro.values()) {
			if (sigla.equals(x.getSigla())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Parceiro inválido: " + sigla);
	}

}
