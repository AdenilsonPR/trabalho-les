describe("Teste: gerir de usuário.", () => {

    it('Ir para o cadastro.', () => {
        cy.visit('/')
        cy.wait(3000)
        cy.get('[data-cy=menu]').click()
        cy.wait(1000)
        cy.get('[data-cy=entrar]').click()
        cy.get('[data-cy=cadastro]').click()
    });

    it('Dados pessoais.', () => {
        cy.wait(3000)
        cy.get('[data-cy=nome]').type('Meu Nome')
        cy.get('[data-cy=cpf]').type('637.874.880-22')
        cy.get('[data-cy=genero]').click()
        cy.wait(1000)
        cy.contains('div', 'Masculino').parent('div').click()
        cy.get('[data-cy=nascimento]').type('10/10/1995')
        cy.get('[data-cy=continuar]').click()
    });

    it('Dados telefone.', () => {
        cy.wait(3000)
        cy.get('[data-cy=tipo]').click()
        cy.wait(1000)
        cy.contains('div', 'Celular').parent('div').click()
        cy.get('[data-cy=ddd]').type('11')
        cy.get('[data-cy=numero]').type('97070-7070')
        cy.get('[data-cy=continuar]').click()
    });

    it('Dados email.', () => {
        cy.wait(3000)
        cy.get('[data-cy=email]').type('meu@email.com')
        cy.get('[data-cy=senha]').type('Abc123456;')
        cy.get('[data-cy=continuar]').click()
    });


})