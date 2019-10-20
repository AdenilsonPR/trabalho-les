describe("Teste: gerir compra.", () => {

    it('Efetuar login.', () => {
        cy.visit('/')
        cy.wait(3000)
        cy.get('[data-cy=menu]').click()
        cy.wait(1000)
        cy.get('[data-cy=entrar]').click()
        cy.get('[data-cy=email]').type('admin@email.com')
        cy.get('[data-cy=senha]').type('Admin123;')
        cy.wait(1000)
        cy.get('[data-cy=confirmar]').click()
    });

    it('Mudar situação da compra.', () => {
        cy.wait(1000)
        cy.contains('div', 'Vendas').parent('div').click()
        cy.wait(3000)
        cy.get('[data-cy=pesquisar]').type('112510')
        cy.wait(3000)
        cy.get('td>i').eq(-1).click()
        cy.wait(3000)
        cy.get('td>i').eq(-1).click()
        cy.contains('div', 'Entregue').parent('div').click()
        cy.wait(3000)
        cy.get('[data-cy=confirmar]').click()
        cy.wait(3000)
        cy.get('[data-cy=sair]').click()

    });
})