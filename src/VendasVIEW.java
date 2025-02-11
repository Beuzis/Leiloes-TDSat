/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Isabela
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class VendasVIEW extends JFrame {
    
    private JTable tabelaVendas; // A tabela para exibir os produtos vendidos
    private DefaultTableModel modeloTabela; // Modelo da tabela
    
    // Construtor da classe
    public VendasVIEW() {
        // Configurações da janela
        setTitle("Produtos Vendidos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Para centralizar a tela
        
        // Criar o modelo da tabela
        modeloTabela = new DefaultTableModel();
        tabelaVendas = new JTable(modeloTabela);
        
        // Adicionar as colunas na tabela
        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("Valor");
        modeloTabela.addColumn("Status");
        
        // Criar um JScrollPane para a tabela
        JScrollPane scrollPane = new JScrollPane(tabelaVendas);
        
        // Adicionar o scrollPane à janela
        add(scrollPane, BorderLayout.CENTER);
        
        // Carregar os dados na tabela
        carregarProdutosVendidos();
    }

    // Método para carregar os produtos vendidos
    public void carregarProdutosVendidos() {
        // Criar um objeto da classe ProdutosDAO
        ProdutosDAO produtosDAO = new ProdutosDAO();
        
        // Buscar os produtos vendidos
        ArrayList<ProdutosDTO> listaVendidos = produtosDAO.listarProdutosVendidos();
        
        // Limpar dados antigos na tabela (se houver)
        modeloTabela.setRowCount(0);
        
        // Adicionar os produtos vendidos à tabela
        for (ProdutosDTO produto : listaVendidos) {
            // Adicionar uma nova linha para cada produto
            modeloTabela.addRow(new Object[]{
                produto.getId(),
                produto.getNome(),
                produto.getValor(),
                produto.getStatus()
            });
        }
    }

    // Método principal para exibir a tela
    public static void main(String[] args) {
        // Criação da tela de Vendas
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Criar a instância da classe VendasVIEW
                VendasVIEW telaVendas = new VendasVIEW();
                telaVendas.setVisible(true); // Tornar a tela visível
            }
        });
    }
}

