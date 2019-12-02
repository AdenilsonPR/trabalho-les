describe("Teste: efetuar uma compra.", () => {

    it('Efetuar login.', () => {
        cy.visit('/')
        cy.wait(3000)
        cy.get('[data-cy=menu]').click()
        cy.wait(1000)
        cy.get('[data-cy=entrar]').click()
        cy.get('[data-cy=email]').type('meu@email.com')
        cy.get('[data-cy=senha]').type('Abc123456;')
        cy.wait(1000)
        cy.get('[data-cy=confirmar]').click()
    });

    it('Escolher material.', () => {
        cy.wait(3000)
        cy.contains('div', 'Material 01').parent('div').click()
        cy.get('[data-cy=material-0]').click()
        cy.wait(1000)
        cy.get('[data-cy=quantidade]').type(0)
        cy.wait(1000)
        cy.get('[data-cy=adicionar-carrinho]').click()
    });

    it('Ir para carrinho de compra.', () => {
        cy.get('[data-cy=carrinho-compra]').click()
        cy.wait(1000)
        cy.get('[data-cy=ir-carrinho-compra]').click()
    });

    it('Gerenciar carrinho de compra.', () => {
        cy.wait(1000)
        cy.get('[data-cy=quantidade-0]').type(0)
        cy.get('[data-cy=cep]').type('08000-000')
        cy.wait(1000)
        cy.get('[data-cy=continuar]').click()
    });

    it('Gerenciar forma de pagamento.', () => {
        cy.get('[data-cy=tipo-pagamento]').click()
        cy.contains('div', 'Boleto').parent('div').click()
        cy.wait(3000)
        cy.get('[data-cy=tipo-pagamento]').click()
        cy.contains('div', 'Cartão').parent('div').click()
        cy.get('[data-cy=cupons]').click()
        cy.wait(500)
        cy.contains('div', '10').parent('div').click()
        cy.wait(500)
        cy.focused().type('{esc}')

        cy.get('[data-cy=cartoes]').click()
        cy.wait(1000)
        cy.contains('div', '1111 1111 1111 1111').click()
        cy.wait(1000)
        cy.get('[data-cy=valor]').type('1000')
        cy.wait(1000)
        cy.get('[data-cy=add]').click()

        cy.get('[data-cy=nome]').type('Meu nome')
        cy.get('[data-cy=numero]').type('1234 1234 1234 1234')
        cy.get('[data-cy=bandeira]').click()
        cy.contains('div', 'Bradesco').parent('div').click()
        cy.get('[data-cy=codigo]').type('123')
        cy.get('[data-cy=valor]').type('500')
        cy.wait(1000)
        cy.get('[data-cy=add]').click()
        cy.wait(1000)
        cy.get('[data-cy=continuar]').click()
    });

    it('Gerenciar endereço.', () => {
        cy.get('[data-cy=enderecos]').click()
        cy.wait(1000)
        cy.contains('div', 'Minha rua - Nº 2212').parent('div').click()
        cy.wait(1000)
        cy.get('[data-cy=add]').click()

        cy.get('[data-cy=cep]').type('08000-000')
        cy.get('[data-cy=logradouro]').type('Minha outra rua')
        cy.get('[data-cy=bairro]').type('Meu outro bairro')
        cy.get('[data-cy=cidade]').type('Minha outra cidade')
        cy.get('[data-cy=estado]').click()
        cy.contains('div', 'São Paulo').parent('div').click()
        cy.get('[data-cy=pais]').click()
        cy.contains('div', 'Brasil').parent('div').click()
        cy.get('[data-cy=numero]').type('000')
        cy.get('[data-cy=tipo-residencia]').click()
        cy.contains('div', 'Casa').parent('div').click()
        cy.get('[data-cy=tipo-logradouro]').click()
        cy.contains('div', 'Rua').parent('div').click()
        cy.get('[data-cy=entrega]').click()
        cy.contains('div', 'Sim').parent('div').click()
        cy.get('[data-cy=cobranca]').click()
        cy.contains('div', 'Não').parent('div').click()
        cy.wait(1000)
        cy.get('[data-cy=add]').click()
        cy.wait(1000)
        cy.get('[data-cy=continuar]').click()
    });

    it('Finalizar compra.', () => {
        cy.wait(1000)
        cy.get('[data-cy=continuar]').click()
        cy.wait(1000)
        cy.get('[data-cy=confirmar]').click()
        cy.wait(1000)
        cy.get('[data-cy=menu]').click()
        cy.wait(1000)
        cy.get('[data-cy=sair]').click()
    });
})