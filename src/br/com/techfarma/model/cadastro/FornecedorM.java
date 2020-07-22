package br.com.techfarma.model.cadastro;

public class FornecedorM {
    private int idFornecedor;
    private String razaoSocial, nome, endereco, numero;
    private String bairro, complemento, cidade, CEP;
    private int codPais;
    private String UF;
    private int codMunicipio;
    private String CNPJ, insEstadual;
    private double limiteCred;
    private int codFaturamento;
    private String vendedor, telefone, email, website;
    private double contaCont;
    private String CFOP;
    private String enderecoCob, bairroCob, cidadeCob, CEPCob, UFCob, config, servidor, usuario, senha, dirEnvio, dirRetorno, arqEnvio, arqRetorno, diretorioPE;
    private int codDic;
    private String somaICMS, somaIPI, somaFrete, somaSeguro, somaOutrasDesp;

    public FornecedorM(String razaoSocial, String nome, String endereco, String numero, String bairro, String complemento, String cidade, String CEP, int codPais, String UF, int codMunicipio, String CNPJ, String insEstadual, double limiteCred, int codFaturamento, String vendedor, String telefone, String email, String website, double contaCont, String CFOP, String enderecoCob, String bairroCob, String cidadeCob, String CEPCob, String UFCob, String config, String servidor, String usuario, String senha, String dirEnvio, String dirRetorno, String arqEnvio, String arqRetorno, String diretorioPE, int codDic, String somaICMS, String somaIPI, String somaFrete, String somaSeguro, String somaOutrasDesp) {
        this.razaoSocial = razaoSocial;
        this.nome = nome;
        this.endereco = endereco;
        this.numero = numero;
        this.bairro = bairro;
        this.complemento = complemento;
        this.cidade = cidade;
        this.CEP = CEP;
        this.codPais = codPais;
        this.UF = UF;
        this.codMunicipio = codMunicipio;
        this.CNPJ = CNPJ;
        this.insEstadual = insEstadual;
        this.limiteCred = limiteCred;
        this.codFaturamento = codFaturamento;
        this.vendedor = vendedor;
        this.telefone = telefone;
        this.email = email;
        this.website = website;
        this.contaCont = contaCont;
        this.CFOP = CFOP;
        this.enderecoCob = enderecoCob;
        this.bairroCob = bairroCob;
        this.cidadeCob = cidadeCob;
        this.CEPCob = CEPCob;
        this.UFCob = UFCob;
        this.config = config;
        this.servidor = servidor;
        this.usuario = usuario;
        this.senha = senha;
        this.dirEnvio = dirEnvio;
        this.dirRetorno = dirRetorno;
        this.arqEnvio = arqEnvio;
        this.arqRetorno = arqRetorno;
        this.diretorioPE = diretorioPE;
        this.codDic = codDic;
        this.somaICMS = somaICMS;
        this.somaIPI = somaIPI;
        this.somaFrete = somaFrete;
        this.somaSeguro = somaSeguro;
        this.somaOutrasDesp = somaOutrasDesp;
    }

    public int getIdFornecedor() {
        return idFornecedor;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getNumero() {
        return numero;
    }
    
    public String getBairro() {
        return bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public String getCEP() {
        return CEP;
    }

    public int getCodPais() {
        return codPais;
    }

    public String getUF() {
        return UF;
    }

    public int getCodMunicipio() {
        return codMunicipio;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public String getInsEstadual() {
        return insEstadual;
    }

    public double getLimiteCred() {
        return limiteCred;
    }

    public int getCodFaturamento() {
        return codFaturamento;
    }

    public String getVendedor() {
        return vendedor;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public String getWebsite() {
        return website;
    }

    public double getContaCont() {
        return contaCont;
    }

    public String getCFOP() {
        return CFOP;
    }

    public String getEnderecoCob() {
        return enderecoCob;
    }

    public String getBairroCob() {
        return bairroCob;
    }

    public String getCidadeCob() {
        return cidadeCob;
    }

    public String getCEPCob() {
        return CEPCob;
    }

    public String getUFCob() {
        return UFCob;
    }

    public String getConfig() {
        return config;
    }

    public String getServidor() {
        return servidor;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }

    public String getDirEnvio() {
        return dirEnvio;
    }

    public String getDirRetorno() {
        return dirRetorno;
    }

    public String getArqEnvio() {
        return arqEnvio;
    }

    public String getArqRetorno() {
        return arqRetorno;
    }

    public String getDiretorioPE() {
        return diretorioPE;
    }

    public int getCodDic() {
        return codDic;
    }

    public String getSomaICMS() {
        return somaICMS;
    }

    public String getSomaIPI() {
        return somaIPI;
    }

    public String getSomaFrete() {
        return somaFrete;
    }

    public String getSomaSeguro() {
        return somaSeguro;
    }

    public String getSomaOutrasDesp() {
        return somaOutrasDesp;
    }
}
