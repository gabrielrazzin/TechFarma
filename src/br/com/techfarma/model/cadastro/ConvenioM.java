package br.com.techfarma.model.cadastro;

public class ConvenioM {
    private int idConvenio, numero, codMun, diaFechamento, diaVencimento, idVendedor, numCobranca;
    private String nome,endereco, bairro, complemento, UF, cidade, CEP, telefone, celular, CNPJ, inscEstadual, RG, CPF, email, vendaFilial, pagaComissao, endCobranca, bairroCobranca, UFCobranca, cidadeCobranca, status;
    private double limCredito, desconto;

    public ConvenioM(int numero, int codMun, int diaFechamento, int diaVencimento, int idVendedor, int numCobranca, String nome, String endereco, String bairro, String complemento, String UF, String cidade, String CEP, String telefone, String celular, String CNPJ, String inscEstadual, String RG, String CPF, String email, String vendaFilial, String pagaComissao, String endCobranca, String bairroCobranca, String UFCobranca, String cidadeCobranca, String status, double limCredito, double desconto) {
        this.numero = numero;
        this.codMun = codMun;
        this.diaFechamento = diaFechamento;
        this.diaVencimento = diaVencimento;
        this.idVendedor = idVendedor;
        this.numCobranca = numCobranca;
        this.nome = nome;
        this.endereco = endereco;
        this.bairro = bairro;
        this.complemento = complemento;
        this.UF = UF;
        this.cidade = cidade;
        this.CEP = CEP;
        this.telefone = telefone;
        this.celular = celular;
        this.CNPJ = CNPJ;
        this.inscEstadual = inscEstadual;
        this.RG = RG;
        this.CPF = CPF;
        this.email = email;
        this.vendaFilial = vendaFilial;
        this.pagaComissao = pagaComissao;
        this.endCobranca = endCobranca;
        this.bairroCobranca = bairroCobranca;
        this.UFCobranca = UFCobranca;
        this.cidadeCobranca = cidadeCobranca;
        this.status = status;
        this.limCredito = limCredito;
        this.desconto = desconto;
    }

    public int getIdConvenio() {
        return idConvenio;
    }

    public int getNumero() {
        return numero;
    }

    public int getCodMun() {
        return codMun;
    }

    public int getDiaFechamento() {
        return diaFechamento;
    }

    public int getDiaVencimento() {
        return diaVencimento;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public int getNumCobranca() {
        return numCobranca;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getUF() {
        return UF;
    }

    public String getCidade() {
        return cidade;
    }

    public String getCEP() {
        return CEP;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCelular() {
        return celular;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public String getInscEstadual() {
        return inscEstadual;
    }

    public String getRG() {
        return RG;
    }

    public String getCPF() {
        return CPF;
    }

    public String getEmail() {
        return email;
    }

    public String getVendaFilial() {
        return vendaFilial;
    }

    public String getPagaComissao() {
        return pagaComissao;
    }

    public String getEndCobranca() {
        return endCobranca;
    }

    public String getBairroCobranca() {
        return bairroCobranca;
    }

    public String getUFCobranca() {
        return UFCobranca;
    }

    public String getCidadeCobranca() {
        return cidadeCobranca;
    }

    public String getStatus() {
        return status;
    }

    public double getLimCredito() {
        return limCredito;
    }

    public double getDesconto() {
        return desconto;
    }
}