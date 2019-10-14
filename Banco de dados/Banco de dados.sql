DROP TABLE telefone;
DROP TABLE login;
DROP TABLE endereco;
DROP TABLE cartao;
DROP TABLE cupomtroca;
DROP TABLE cupompromocional;
DROP TABLE item;
DROP TABLE material;
DROP TABLE venda;
DROP TABLE usuario;
DROP TABLE fornecedor;
DROP TABLE grupoprecificacao;
DROP TABLE frete;

-- Usuario
CREATE TABLE usuario
(
    id SERIAL NOT NULL,
    dataCadastro CHARACTER VARYING(30) NOT NULL,
    status CHARACTER VARYING(8) NOT NULL,
    nome CHARACTER VARYING(30) NOT NULL,
    genero CHARACTER VARYING(9) NOT NULL,
    nascimento CHARACTER VARYING(10) NOT NULL,
    cpf CHARACTER VARYING(14) NOT NULL,
    papel CHARACTER VARYING(13) NOT NULL,
    rank INTEGER NOT NULL,

    CONSTRAINT usuario_pk PRIMARY KEY(id)
);

-- Fornecedor
CREATE TABLE fornecedor
(
    id SERIAL NOT NULL,
    dataCadastro CHARACTER VARYING(30) NOT NULL,
    status CHARACTER VARYING(8) NOT NULL,
    nome CHARACTER VARYING(30) NOT NULL,
    cnpj CHARACTER VARYING(18) NOT NULL,
    categoriafornecida CHARACTER VARYING(30) NOT NULL,

    CONSTRAINT fornecedor_pk PRIMARY KEY(id)
);

-- Grupo precificacao
CREATE TABLE grupoprecificacao
(
    id SERIAL NOT NULL,
    dataCadastro CHARACTER VARYING(30) NOT NULL,
    status CHARACTER VARYING(8) NOT NULL,
    precificacao CHARACTER VARYING(30) NOT NULL,
    porcentagem NUMERIC NOT NULL,

    CONSTRAINT grupoprecificacao_pk PRIMARY KEY(id)
);

-- Frete
CREATE TABLE frete
(
    id SERIAL NOT NULL,
    dataCadastro CHARACTER VARYING(30) NOT NULL,
    status CHARACTER VARYING(8) NOT NULL,
    cep CHARACTER VARYING(9) NOT NULL,
    valor NUMERIC NOT NULL,

    CONSTRAINT frete_pk PRIMARY KEY(id)
);

CREATE TABLE cartao
(
    id SERIAL NOT NULL,
    dataCadastro CHARACTER VARYING(30) NOT NULL,
    status CHARACTER VARYING(8) NOT NULL,
    numero CHARACTER VARYING(19) NOT NULL,
    nome CHARACTER VARYING(30) NOT NULL,
    bandeira CHARACTER VARYING(30) NOT NULL,
    codigo CHARACTER VARYING(3) NOT NULL,
    usuario INTEGER NOT NULL,

    CONSTRAINT cartao_pk PRIMARY KEY(id),
    CONSTRAINT cartao_usuario_fk FOREIGN KEY (usuario) REFERENCES usuario (id)
);

CREATE TABLE login
(
    id SERIAL NOT NULL,
    dataCadastro CHARACTER VARYING(30) NOT NULL,
    status CHARACTER VARYING(8) NOT NULL,
    email CHARACTER VARYING(30) NOT NULL,
    senha CHARACTER VARYING(30) NOT NULL,
    usuario INTEGER NOT NULL,

    CONSTRAINT login_pk PRIMARY KEY(id),
    CONSTRAINT login_usuario_fk FOREIGN KEY(usuario) REFERENCES usuario(id)
);

CREATE TABLE telefone
(
    id SERIAL NOT NULL,
    dataCadastro CHARACTER VARYING(30) NOT NULL,
    status CHARACTER VARYING(8) NOT NULL,
    tipo CHARACTER VARYING(30) NOT NULL,
    ddd CHARACTER VARYING(2) NOT NULL,
    numero CHARACTER VARYING(10) NOT NULL,
    usuario INTEGER NOT NULL,

    CONSTRAINT telefone_pk PRIMARY KEY(id),
    CONSTRAINT telefone_usuario_fk FOREIGN KEY (usuario) REFERENCES usuario (id)
);

CREATE TABLE endereco
(
    id SERIAL NOT NULL,
    dataCadastro CHARACTER VARYING(30) NOT NULL,
    status CHARACTER VARYING(8) NOT NULL,
    cep CHARACTER VARYING(14) NOT NULL,
    logradouro CHARACTER VARYING(30) NOT NULL,
    bairro CHARACTER VARYING(30) NOT NULL,
    cidade CHARACTER VARYING(30) NOT NULL,
    estado CHARACTER VARYING(30) NOT NULL,
    pais CHARACTER VARYING(30) NOT NULL,
    numero CHARACTER VARYING(10) NOT NULL,
    tipoResidencia CHARACTER VARYING(16) NOT NULL,
    tipoLogradouro CHARACTER VARYING(16) NOT NULL,
    cobranca CHARACTER VARYING(3) NOT NULL,
    entrega CHARACTER VARYING(3) NOT NULL,
    usuario INTEGER NOT NULL,

    CONSTRAINT endereco_pk PRIMARY KEY(id),
    CONSTRAINT endereco_usuario_fk FOREIGN KEY(usuario) REFERENCES usuario(id)
);

-- Material
CREATE TABLE material
(
    id SERIAL NOT NULL,
    dataCadastro CHARACTER VARYING(30) NOT NULL,
    status CHARACTER VARYING(8) NOT NULL,
    descricao CHARACTER VARYING(30) NOT NULL,
    categoria CHARACTER VARYING(30) NOT NULL,
    fornecedor INTEGER NOT NULL,
    precificacao INTEGER NOT NULL,
    imagem CHARACTER VARYING(50),
    custo NUMERIC NOT NULL,
    valorvenda NUMERIC NOT NULL,
    quantidade INTEGER NOT NULL,
    motivo CHARACTER VARYING(255),

    CONSTRAINT material_pk PRIMARY KEY(id)
);

-- Venda
CREATE TABLE venda
(
    id SERIAL NOT NULL,
    dataCadastro CHARACTER VARYING(30) NOT NULL,
    status CHARACTER VARYING(8) NOT NULL,
    situacao CHARACTER VARYING(30) NOT NULL,
    total NUMERIC NOT NULL,
    usuario INTEGER NOT NULL,

    CONSTRAINT venda_pk PRIMARY KEY(id),
    CONSTRAINT venda_usuario_fk FOREIGN KEY(usuario) REFERENCES usuario(id)
);

CREATE TABLE item
(
    id SERIAL NOT NULL,
    dataCadastro CHARACTER VARYING(30) NOT NULL,
    status CHARACTER VARYING(8) NOT NULL,
    situacao CHARACTER VARYING(16) NOT NULL,
    papel CHARACTER VARYING(7) NOT NULL,
    descricao CHARACTER VARYING(30) NOT NULL,
    motivo CHARACTER VARYING(30),
    descricaomotivo CHARACTER VARYING(256),
    quantidade INTEGER NOT NULL,
    valortotal NUMERIC NOT NULL,
    valorvenda NUMERIC NOT NULL,
    material INTEGER NOT NULL,
    venda INTEGER NOT NULL,
    usuario INTEGER NOT NULL,

    CONSTRAINT item_pk PRIMARY KEY(id),
    CONSTRAINT item_material_fk FOREIGN KEY(material) REFERENCES material(id),
    CONSTRAINT item_usuario_fk FOREIGN KEY(usuario) REFERENCES usuario(id),
    CONSTRAINT item_venda_fk FOREIGN KEY(venda) REFERENCES venda(id)
);

-- Cupons
CREATE TABLE cupomtroca
(
    id SERIAL NOT NULL,
    dataCadastro CHARACTER VARYING(30) NOT NULL,
    status CHARACTER VARYING(8) NOT NULL,
    valor NUMERIC NOT NULL,
    usuario INTEGER NOT NULL,

    CONSTRAINT cupomtroca_pk PRIMARY KEY(id),
    CONSTRAINT cupomtroca_usuario_fk FOREIGN KEY(usuario) REFERENCES usuario(id)
);

CREATE TABLE cupompromocional
(
    id SERIAL NOT NULL,
    dataCadastro CHARACTER VARYING(30) NOT NULL,
    status CHARACTER VARYING(8) NOT NULL,
    codigo CHARACTER VARYING(30) NOT NULL,
    valor NUMERIC NOT NULL,

    CONSTRAINT cupompromocional_pk PRIMARY KEY(id)
);

-- Insere o administrador
INSERT INTO usuario
    (datacadastro, status, nome, genero, nascimento, cpf, papel,rank)
VALUES
    ('00/00/0000', 'Ativo', 'admin', 'Feminino', '00/00/0000', '000.000.000-00', 'Administrador', 0);

INSERT INTO login
    (datacadastro, status, email, senha, usuario)
VALUES
    ('00/00/0000', 'Ativo', 'admin@email.com', 'zLzCz17YAzz4S2zPzKz2Gz6ONWYzzV', 1);

-- Insere materiais
-- INSERT INTO material
--     (datacadastro, status, descricao, categoria, fornecedor, precificacao, imagem, custo, valorvenda, quantidade)
-- VALUES
--     ('00/00/0000', 'Ativo', 'Veralhão 10 mm', 'Construção', 'Fornecedor', 'Precificação', '../../assets/vergalhão.jpg', 30.00, 40.00, 500);

-- INSERT INTO material
--     (datacadastro, status, descricao, categoria, fornecedor, precificacao, imagem, custo, valorvenda, quantidade)
-- VALUES
--     ('00/00/0000', 'Ativo', 'Veralhão 8 mm', 'Construção', 'Fornecedor', 'Precificação', '../../assets/vergalhão.jpg', 20.00, 27.50, 300);

-- INSERT INTO material
--     (datacadastro, status, descricao, categoria, fornecedor, precificacao, imagem, custo, valorvenda, quantidade)
-- VALUES
--     ('00/00/0000', 'Ativo', 'Veralhão 4.2 mm', 'Construção', 'Fornecedor', 'Precificação', '../../assets/vergalhão.jpg', 10.00, 15.00, 1000);    

