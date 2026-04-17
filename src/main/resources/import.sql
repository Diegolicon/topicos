INSERT INTO enderecos (id, rua, numero, cidade, estado, cep, complemento) VALUES
  (1, 'Rua A', '123', 'São Paulo', 'SP', '01000-000', 'Apto 101'),
  (2, 'Av. B', '456', 'Rio de Janeiro', 'RJ', '22000-000', 'Casa');

INSERT INTO usuarios (id, nome, email, telefone, endereco_id) VALUES
  (1, 'Carlos Silva', 'carlos.silva@example.com', '11987654321', 1),
  (2, 'Mariana Oliveira', 'mariana.oliveira@example.com', '21999988877', 2);

INSERT INTO produtos (id, nome, descricao, preco, estoque, marca, tipo_produto) VALUES
  (1, 'Munição BB 0.20g', 'Bolinha de plástico para treino', 49.90, 120, 'AirTec', 'Produto'),
  (2, 'Bateria 9.6V', 'Bateria recarregável para AEG', 129.90, 35, 'PowerMax', 'Produto');

INSERT INTO produtos (id, nome, descricao, preco, estoque, marca, tipo_produto, tipo_propulsao, modelo, velocidade_escopeta, alcance_efetivo) VALUES
  (3, 'Rifle AEG Tático', 'Rifle elétrico full metal', 1599.90, 8, 'Viper', 'ARMA_AIRSOFT', 'Viper X9', 'AEG', 120.0, 45.0),
  (4, 'Pistola GBB Compact', 'Pistola gas blow back', 899.90, 5, 'Strike', 'ARMA_AIRSOFT', 'Strike P45', 'GBB', 95.0, 25.0);

INSERT INTO vendas (id, usuario_id, total_venda, status, observacoes) VALUES
  (1, 1, 249.79, 'PENDENTE', 'Retirar na loja física.');

INSERT INTO itens_venda (id, venda_id, produto_id, quantidade, preco_unitario) VALUES
  (1, 1, 1, 2, 49.90),
  (2, 1, 3, 1, 149.99);
