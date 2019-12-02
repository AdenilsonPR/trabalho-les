describe("Teste: visualizar pedido de troca e cupom.", () => {

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

    it('Ir para o perfil.', () => {
        cy.wait(3000)
        cy.get('[data-cy=menu]').click()
        cy.get('[data-cy=perfil]').click()
    });

    it('Visualizar pedido de troca.', () => {
        cy.wait(3000)
        cy.contains('div', 'Trocas').parent('div').click()
        cy.wait(3000)
        cy.get('td>i').eq(-1).click()
        cy.wait(3000)
        cy.get('[data-cy=cancelar]').click()
    });

    it('Visualizar cupom.', () => {
        cy.wait(3000)
        cy.contains('div', 'Cupons').parent('div').click()
        cy.wait(3000)
    });

    it('Visualizar endereço.', () => {
        cy.contains('div', 'Endereços').parent('div').click()
        cy.wait(3000)
    });

    it('Visualizar cartão.', () => {
        cy.contains('div', 'Cartões').parent('div').click()
        cy.wait(3000)
        cy.get('[data-cy=sair]').click()
    });
})