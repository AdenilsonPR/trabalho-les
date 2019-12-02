describe("Teste: efetuar uma compra com cupom.", () => {

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
        cy.get('[data-cy=quantidade]').type(1)
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
        cy.wait(3000)
        cy.get('[data-cy=tipo-pagamento]').click()
        cy.contains('div', 'Cartão').parent('div').click()
        cy.get('[data-cy=cupons]').click()
        cy.wait(500)
        cy.contains('div', '1250').parent('div').click()
        cy.wait(500)
        // cy.focused().type('{esc}')

        cy.get('[data-cy=cartoes]').click()
        cy.wait(1000)
        cy.contains('div', '1234 1234 1234 1234').click()
        cy.wait(1000)
        cy.get('[data-cy=valor]').type('123500')
        cy.wait(1000)
        cy.get('[data-cy=add]').click()
        cy.wait(1000)
        cy.get('[data-cy=continuar]').click()
    });

    it('Gerenciar endereço.', () => {
        cy.get('[data-cy=enderecos]').click()
        cy.wait(1000)
        cy.contains('div', 'Minha rua - Nº 000').parent('div').click()
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