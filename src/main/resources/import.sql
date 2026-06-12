-- =============================================================================
-- 1. ENDEREÇOS
-- =============================================================================
INSERT INTO enderecos (id, rua, numero, cidade, estado, cep, ativo) VALUES
  (1, 'Rua das Flores', '123', 'Palmas', 'TO', '77000000', true),
  (2, 'Av. JK', '456', 'Palmas', 'TO', '77000001', true),
  (3, 'Av. Teotonio Segurado', '789', 'Palmas', 'TO', '77000002', true);

-- =============================================================================
-- 2. USUÁRIOS
-- =============================================================================
INSERT INTO usuarios (id, nome, email, telefone, senha, role, endereco_id, ativo) VALUES  
  (1, 'Carlos Silva', 'carlos.silva@example.com', '11987654321', '123', 'USER', 1, true),  
  (2, 'Mariana Oliveira', 'mariana.oliveira@example.com', '21999988877', '456', 'USER', 2, true),  
  (3, 'Diego Admin', 'diego@teste.com', '63984352575', '12345678', 'ADMIN', 3, true);

-- =============================================================================
-- 3. PRODUTOS 
-- =============================================================================
INSERT INTO produtos (id, nome, descricao, preco, estoque, marca, tipo_produto, ativo) VALUES   
  (1, 'Municao BB 0.20g', 'Bolinha de plastico para treino', 49.90, 120, 'AirTec', 'PRODUTO', true),  
  (2, 'Bateria 9.6V', 'Bateria recarregavel para AEG', 129.90, 35, 'PowerMax', 'PRODUTO', true),  
  (3, 'Rifle AEG Tatico', 'Rifle eletrico full metal', 1599.90, 8, 'Viper', 'ARMA_AIRSOFT', true),  
  (4, 'Pistola GBB Compact', 'Pistola gas blow back', 899.90, 5, 'Strike', 'ARMA_AIRSOFT', true);

-- =============================================================================
-- 4. ARMAS DE AIRSOFT
-- =============================================================================
INSERT INTO armas_airsoft (id, tipo_propulsao, modelo, velocidade_escopeta, alcance_efetivo) VALUES
  (3, 'AEG', 'M4A1 Carbine', 110.0, 40.0),  
  (4, 'GBB', 'Strike P45', 95.0, 25.0);

-- =============================================================================
-- 5. VENDAS
-- =============================================================================
INSERT INTO vendas (id, usuario_id, total_venda, status, observacoes, ativo) VALUES  
  (1, 1, 249.79, 'PENDENTE', 'Retirar na loja fisica.', true);

-- =============================================================================
-- 6. ITENS DA VENDA
-- =============================================================================
INSERT INTO itens_venda (id, venda_id, produto_id, quantidade, preco_unitario, ativo) VALUES  
  (1, 1, 1, 2, 49.90, true),  
  (2, 1, 3, 1, 149.99, true);

-- Atualiza os ponteiros das sequences para o maior ID + 1
SELECT setval('enderecos_SEQ', (SELECT MAX(id) FROM enderecos));
SELECT setval('produtos_SEQ', (SELECT MAX(id) FROM produtos));
SELECT setval('usuarios_SEQ', (SELECT MAX(id) FROM usuarios));
SELECT setval('vendas_SEQ', (SELECT MAX(id) FROM vendas));
SELECT setval('itens_venda_SEQ', (SELECT MAX(id) FROM itens_venda));