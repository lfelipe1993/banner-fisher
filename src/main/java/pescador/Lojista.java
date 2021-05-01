package pescador;

public enum Lojista {
	CASASBAHIA("Casas Bahia", "https://www.casasbahia-imagens.com.br/HotSite/", "CB"), 
	EXTRA("Extra", "https://www.extra-imagens.com.br/HotSite/", "EX"),
	PONTO("Ponto", "https://www.pontofrio-imagens.com.br/HotSite/", "PF");


	private String descricao;
	private String dominio;
	private String sigla;

	private Lojista(String descricao, String dominio, String sigla) {
		this.descricao = descricao;
		this.dominio = dominio;
		this.sigla = sigla;
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

	public static Lojista toEnum(String descricao) {
		if (descricao == null) {
			return null;
		}

		for (Lojista x : Lojista.values()) {
			if (descricao.equals(x.getDescricao())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Lojista inválido: " + descricao);
	}
	
	public static Lojista toEnumBySigla(String sigla) {
		if (sigla == null) {
			return null;
		}

		for (Lojista x : Lojista.values()) {
			if (sigla.equals(x.getSigla())) {
				return x;
			}
		}

		throw new IllegalArgumentException("Lojista inválido: " + sigla);
	}
}
