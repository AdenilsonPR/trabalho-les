describe("Teste: filtros.", () => {

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

    it('Filtrar dados do gráfico.', () => {
        cy.wait(1000)
        cy.contains('div', 'Estatisticas').parent('div').click()
        cy.wait(3000)
        cy.get('[data-cy=inicio]').type('02/11/2019')
        cy.get('[data-cy=fim]').type('03/11/2019')
        cy.wait(3000)
        cy.get('[data-cy=filtrar]').click()
        cy.wait(3000)
        cy.get('[data-cy=filtrar]').click()
    });
})