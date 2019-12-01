describe("Teste: gerir de usuário.", () => {

    it('Efetuar cadastro.', () => {
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


})