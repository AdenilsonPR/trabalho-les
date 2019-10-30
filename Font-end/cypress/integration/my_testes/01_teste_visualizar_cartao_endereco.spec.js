describe("Teste: visualizar endereço e cartão.", () => {

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

    it('Visualizar endereço.', () => {
        cy.get('[data-cy=menu]').click()
        cy.wait(1000)
        cy.get('[data-cy=perfil]').click()
        cy.wait(3000)
        cy.contains('div', 'Endereços').parent('div').click()
        cy.wait(3000)
    });

    it('Visualizar cartão.', () => {
        cy.contains('div', 'Cartões').parent('div').click()
        cy.wait(3000)
    });

    it('Visualizar cupom.', () => {
        cy.contains('div', 'Cupons').parent('div').click()
        cy.wait(3000)
        cy.get('[data-cy=sair]').click()
    });
})