import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {

    // Login.
    stateLogin: {
      id: '',
      dataCadastro: '',
      status: '',
      email: '',
      senha: '',
      usuario: ''
    },

    // Usuario
    stateUsuario: JSON.parse(localStorage.getItem("usuario")),

    // Compra.
    stateItens: [],
    stateResumoCompra: {
      valorProdutos: '',
      valorFrete: '',
      valorNovoCupom: '',
      valorDesconto: '',
      valorTotal: ''
    },
    stateCuponsUtilizados: [],

    // Cartão.
    stateCartoes: [],

    // Endereço.
    stateEnderecos: []

  },
  mutations: {

    // Login.
    getLogin(state, payload) {
      state.stateLogin.id = payload.id
      state.stateLogin.dataCadastro = payload.dataCadastro
      state.stateLogin.status = payload.status
      state.stateLogin.email = payload.email
      state.stateLogin.senha = payload.senha
      state.stateLogin.usuario = payload.pessoa
    },

    // Usuario.
    getUsuario(state, payload) {
      state.stateUsuario = payload
    },

    // Compra.
    getCarrinho(state, payload) {
      state.stateItens = payload
    },

    getResumoCompra(state, payload) {
      state.stateResumoCompra.valorProdutos = payload.valorProdutos
      state.stateResumoCompra.valorFrete = payload.valorFrete
      state.stateResumoCompra.valorNovoCupom = payload.valorNovoCupom
      state.stateResumoCompra.valorDesconto = payload.valorDesconto
      state.stateResumoCompra.valorTotal = payload.valorTotal
    },

    getCuponsUtilizados(state, payload) {
      state.stateCuponsUtilizados = payload
    },

    // Cartão.
    getCartoes(state, payload) {
      state.stateCartoes = payload
    },

    // Endereço.
    getEnderecos(state, payload) {
      state.stateEnderecos = payload
    }


  },

  actions: {

  }
})