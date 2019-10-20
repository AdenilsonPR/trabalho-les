describe("Teste: efeturar pedido de troca.", () => {

    it('Efetuar login.', () => {
        cy.visit('/')
        cy.wait(3000)
        cy.get('[data-cy=menu]').click()
        cy.wait(1000)
        cy.get('[data-cy=entrar]').click()
        cy.get('[data-cy=email]').type('email')
        cy.get('[data-cy=senha]').type('Abc123456;')
        cy.wait(1000)
        cy.get('[data-cy=confirmar]').click()
    });

    it('Fazer pedido de troca.', () => {
        cy.wait(3000)
        cy.get('[data-cy=menu]').click()
        cy.get('[data-cy=perfil]').click()
        cy.wait(1000)
        cy.contains('div', 'Compras').parent('div').click()
        cy.wait(3000)
        cy.get('[data-cy=pesquisar]').type('112510')
        cy.wait(3000)
        cy.get('td>i').eq(-1).click()
        cy.wait(3000)
        cy.get('[data-cy=visualizar]').click()
        cy.wait(3000)
        cy.get('[data-cy=trocar]').click()
        cy.wait(3000)
        cy.get('[data-cy=motivo]').click()
        cy.wait(1000)
        cy.contains('div', 'Defeito').parent('div').click()
        cy.get('[data-cy=descricao]').type('O material foi entregue quebrado.')
        cy.wait(3000)
        cy.get('[data-cy=confirmar]').click()
        cy.get('[data-cy=cancelar]').click()
        cy.wait(3000)
        cy.get('[data-cy=sair]').click()


    });
})