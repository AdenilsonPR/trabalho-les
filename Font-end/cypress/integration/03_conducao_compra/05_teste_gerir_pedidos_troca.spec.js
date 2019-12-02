describe("Teste: gerir pedido de troca.", () => {

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

    it('Mudar situação da troca.', () => {
        cy.wait(1000)
        cy.contains('div', 'Trocas').parent('div').click()
        cy.wait(3000)
        cy.get('td>i').eq(-1).click()
        cy.wait(3000)
        cy.get('[data-cy=situacao]').click()
        cy.wait(3000)
        cy.contains('div', 'Troca autorizada').parent('div').click()
        cy.wait(3000)
        cy.get('[data-cy=confirmar]').click()
        cy.wait(3000)
        cy.get('[data-cy=sair]').click()
    });
})