/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;

    // Método para listar os produtos
    public ArrayList<ProdutosDTO> listarProdutos() {
        ArrayList<ProdutosDTO> listagem = new ArrayList<>(); // Lista de produtos

        conn = new conectaDAO().connectDB(); // Conectar ao banco

        try {
            // Query SQL para listar os produtos
            String sql = "SELECT * FROM produtos";
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            // Preencher a lista com os resultados
            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));

                // Verificando o tipo de dado e tratando para o valor
                String valorString = resultset.getString("valor"); // Pega o valor como String
                if (valorString != null && !valorString.isEmpty()) {
                    produto.setValor(Double.parseDouble(valorString)); // Converte para Double
                } else {
                    produto.setValor(0.0); // Caso o valor seja nulo ou vazio, atribui 0.0
                }

                produto.setStatus(resultset.getString("status"));

                listagem.add(produto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Fechar a conexão
                if (resultset != null) resultset.close();
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return listagem; // Retorna a lista com todos os produtos
    }

    // Método para cadastrar um novo produto
    public void cadastrarProduto(ProdutosDTO produto) {
        conn = new conectaDAO().connectDB(); // Conectar ao banco

        try {
            // Query SQL para inserir um novo produto no banco
            String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome()); // Define o nome do produto
            prep.setDouble(2, produto.getValor()); // Define o valor do produto
            prep.setString(3, produto.getStatus()); // Define o status do produto

            // Executar o comando SQL
            int rowsAffected = prep.executeUpdate();

            // Verificar se a inserção foi bem-sucedida
            if (rowsAffected > 0) {
                System.out.println("Produto cadastrado com sucesso!");
            } else {
                System.out.println("Falha ao cadastrar o produto.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar o produto: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                // Fechar a conexão
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Método para vender produto (alterar status para 'Vendido')
    public void venderProduto(int idProduto) {
        String sql = "UPDATE produtos SET status = ? WHERE id = ?"; // SQL para atualizar o status

        try {
            // Prepara a query para alterar o status do produto
            prep = conn.prepareStatement(sql);
            prep.setString(1, "Vendido"); // Define o status como 'Vendido'
            prep.setInt(2, idProduto); // Define o ID do produto a ser atualizado

            int rowsAffected = prep.executeUpdate(); // Executa a atualização

            if (rowsAffected > 0) {
                System.out.println("Produto vendido com sucesso!");
            } else {
                System.out.println("Produto não encontrado ou já vendido.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao vender produto: " + e.getMessage());
        } finally {
            try {
                if (prep != null) prep.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
