package br.com.techfarma.model.cadastro;

import java.sql.Date;

public class FuncionarioM {
    private int idFuncionario, numDependentes;
    private String filial, nome, identidade, CPF, cargo, funcao, endereco, bairro, cidade, UF, CEP, telefone, email, naturalidade, conjugue, nomePai, nomeMae, sexo, estadoCivil, status, loginFP, senhaFP;
    private Date dataNasc, admissao; 
    private double salario;
    
    public FuncionarioM(String filial, String nome, String identidade, String CPF, Date dataNasc, String cargo, String funcao, Date admissao, double salario, String endereco, String bairro, String cidade, String UF, String CEP, String telefone, String email, String naturalidade, String conjugue, int numDependentes, String nomePai, String nomeMae, String sexo, String estadoCivil, String status, String loginFP, String senhaFP){
        this.filial = filial;
        this.nome = nome;
        this.identidade = identidade;
        this.CPF = CPF;
        this.dataNasc = dataNasc;
        this.cargo = cargo;
        this.funcao = funcao;
        this.admissao = admissao;
        this.salario = salario;
        this.endereco = endereco;
        this.bairro = bairro;
        this.cidade = cidade;
        this.UF = UF;
        this.CEP = CEP;
        this.telefone = telefone;
        this.email = email;
        this.naturalidade = naturalidade;
        this.conjugue = conjugue;
        this.numDependentes = numDependentes;
        this.nomePai = nomePai;
        this.nomeMae = nomeMae;
        this.sexo = sexo;
        this.estadoCivil = estadoCivil;
        this.status = status;
        this.loginFP = loginFP;
        this.senhaFP = senhaFP;       
    }

    public FuncionarioM(String filial, String nome, Date dataNasc) {
        this.filial = filial;
        this.nome = nome;
        this.dataNasc = dataNasc;
    }
    
    

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public int getNumDependentes() {
        return numDependentes;
    }

    public String getFilial() {
        return filial;
    }

    public String getNome() {
        return nome;
    }

    public String getIdentidade() {
        return identidade;
    }

    public String getCPF() {
        return CPF;
    }

    public String getCargo() {
        return cargo;
    }

    public String getFuncao() {
        return funcao;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getUF() {
        return UF;
    }

    public String getCEP() {
        return CEP;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public String getConjugue() {
        return conjugue;
    }

    public String getNomePai() {
        return nomePai;
    }

    public String getNomeMae() {
        return nomeMae;
    }

    public String getSexo() {
        return sexo;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public String getStatus() {
        return status;
    }

    public String getLoginFP() {
        return loginFP;
    }

    public String getSenhaFP() {
        return senhaFP;
    }

    public Date getDataNasc() {
        return dataNasc;
    }

    public Date getAdmissao() {
        return admissao;
    }

    public double getSalario() {
        return salario;
    }
}