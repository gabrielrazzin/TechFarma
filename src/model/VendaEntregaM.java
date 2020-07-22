package model;

public class VendaEntregaM {
    private int idCliente;
    private String nome;
    private String telefone;
    private String celular;
    private String entrega;
    private String rua;
    private String numero;
    private String bairro;
    private String complemento;
    private String municipio;
    private String referencia;
    private int idConvenio;

    private String endereco;
    
    public VendaEntregaM(String nome, String telefone, String celular, String endereco, String complemento, String bairro, String municipio, int idConvenio){
        this.nome = nome;
        this.telefone = telefone;
        this.celular = celular;
        this.endereco = endereco;
        this.complemento = complemento;
        this.bairro = bairro;
        this.municipio = municipio;
        this.idConvenio = idConvenio;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCelular() {
        return celular;
    }

    public String getEntrega() {
        return entrega;
    }

    public String getRua() {
        return rua;
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

    public String getReferencia() {
        return referencia;
    }
    
    public String getMunicipio() {
        return municipio;
    }

    public int getIdConvenio() {
        return idConvenio;
    }
    
    public String getEndereco(){
        return endereco;
    }
}
