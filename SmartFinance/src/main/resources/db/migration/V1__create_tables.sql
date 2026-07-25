CREATE TABLE IF NOT EXISTS usuarios (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha_hash VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS contas(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    numero_conta VARCHAR(255) NOT NULL,
    saldo NUMERIC(15, 2) NOT NULL,
    usuario_id UUID NOT NULL REFERENCES usuarios(id),
    CHECK(saldo >= 0)
    );

CREATE TABLE IF NOT EXISTS cartoes(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tipo VARCHAR(20) NOT NULL CHECK (tipo IN('DEBITO', 'CREDITO')),
    numero_final VARCHAR(255) NOT NULL,
    validade DATE NOT NULL,
    nome VARCHAR(255) NOT NULL,
    conta_id UUID NOT NULL REFERENCES contas(id)
);

CREATE TABLE IF NOT EXISTS categorias(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(255) NOT NULL,
    tipo VARCHAR(7) NOT NULL CHECK(tipo IN('RECEITA', 'DESPESA')),
    cor_hex VARCHAR(20) NOT NULL
);


CREATE TABLE IF NOT EXISTS transacoes(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    valor NUMERIC(15, 2) NOT NULL CHECK(valor > 0),
    data DATE NOT NULL,
    tipo VARCHAR(7) NOT NULL CHECK(tipo IN('RECEITA', 'DESPESA')),
    descricao VARCHAR(255),
    conta_id UUID NOT NULL REFERENCES contas(id),
    categoria_id UUID NOT NULL REFERENCES categorias(id),
    cartao_id UUID REFERENCES cartoes(id)
);

CREATE INDEX idx_transacoes_conta_data ON transacoes(conta_id, data);
CREATE INDEX idx_transacoes_categoria ON transacoes(categoria_id);
