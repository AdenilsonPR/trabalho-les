<template>
  <v-app>
    <!-- Toolbar -->
    <v-app-bar app clipped-left dark color="deep-orange accent-4">
      <v-toolbar-title class="headline text-uppercase">
        <span>Materiais</span>
        <span>Construção</span>
      </v-toolbar-title>
      <v-spacer></v-spacer>

      <v-text-field
        class="ml-12"
        v-model="search"
        append-icon="search"
        label="Pesquisar"
        hide-details
      ></v-text-field>
      <v-spacer></v-spacer>

      <!-- Carrinho de compra -->
      <v-badge right bottom v-if="stateUsuario.id !== undefined">
        <v-layout wrap justify-space-around>
          <v-menu transition="slide-y-transition" bottom offset-y>
            <template v-slot:activator="{ on }">
              <v-btn icon v-on="on">
                <v-icon>shopping_cart</v-icon>
              </v-btn>
            </template>
            <v-list>
              <v-list-item v-if="itens.length == 0">
                <span>Carrinho vazio</span>
              </v-list-item>

              <v-list-item v-for="item in itens" :key="item.id">
                <v-list-item-avatar tile>
                  <img :src="require('../../assets/vergalhão.jpg')" />
                </v-list-item-avatar>

                <v-list-item-content>
                  <v-list-item-subtitle>{{item.descricao}}</v-list-item-subtitle>
                  <v-list-item-title>{{item.valorVenda.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})}} x{{item.quantidade}}</v-list-item-title>
                </v-list-item-content>

                <v-list-item-action>
                  <v-btn icon>
                    <v-icon @click="deleteItemCarrinho(item.id)">delete</v-icon>
                  </v-btn>
                </v-list-item-action>
              </v-list-item>
              <v-list-item>
                <v-btn
                  block
                  color="deep-orange accent-4"
                  dark
                  @click="irCarrinhoCompra()"
                >Carrinho de compra</v-btn>
              </v-list-item>
            </v-list>
          </v-menu>
        </v-layout>
        <template v-slot:badge>
          <span>{{contadorItens}}</span>
        </template>
      </v-badge>
      <v-spacer></v-spacer>

      <!-- Menu -->
      <v-layout>
        <v-menu transition="slide-y-transition" bottom offset-y>
          <template v-slot:activator="{ on }">
            <v-btn icon v-on="on" data-cy="menu">
              <v-icon>person</v-icon>
            </v-btn>
          </template>

          <v-list v-if="stateUsuario.id !== undefined">
            <v-list-item>
              <v-btn text :to="{name: 'dashboardClientData'}">Perfil</v-btn>
            </v-list-item>
            <v-list-item>
              <v-btn text :to="{name: 'login'}" data-cy="sair">Sair</v-btn>
            </v-list-item>
          </v-list>

          <v-list v-else>
            <v-list-item>
              <v-btn text :to="{name: 'login'}">Entrar</v-btn>
            </v-list-item>
          </v-list>
        </v-menu>
      </v-layout>
    </v-app-bar>

    <!-- Drawer -->
    <TradeDrawer />

    <!-- Conteudo -->
    <v-container grid-list-lg>
      <v-layout wrap>
        <v-flex v-for="(material, index) in materiais" :key="index" sm3>
          <v-card>
            <v-img
              :src="require('../../assets/vergalhão.jpg')"
              class="white--text"
              height="200px"
              gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
            >
              <v-card-title class="fill-height align-end" v-text="material.descricao"></v-card-title>
            </v-img>

            <v-card-text class="mt-3">
              <v-layout align-baseline>
                <v-flex>
                  <p class="grey--text mt-0 mb-0">{{material.categoria}}</p>
                </v-flex>
                <v-flex>
                  <p
                    class="display-1 text-right mt-0 mb-0"
                  >{{material.valorVenda.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})}}</p>
                </v-flex>
              </v-layout>
            </v-card-text>

            <v-card-actions>
              <v-btn
                block
                color="deep-orange accent-4"
                dark
                @click="visualizar(index, material.id)"
              >Visualizar</v-btn>
            </v-card-actions>
          </v-card>
        </v-flex>
      </v-layout>
    </v-container>

    <!-- Modal Visualizar -->
    <v-dialog v-model="dialog" persistent max-width="720">
      <v-card>
        <v-card-text class="mt-3">
          <v-container grid-list-lg>
            <v-layout wrap>
              <v-flex sm6 class="mt-6">
                <v-img
                  :src="require('../../assets/vergalhão.jpg')"
                  gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
                ></v-img>
              </v-flex>
              <v-flex sm6 class="mt-6">
                <p class="display-1">Descrição:</p>
                <p>{{item.descricao}}</p>
              </v-flex>

              <v-flex sm6>
                <p
                  class="display-1 text-right mt-0 mb-0"
                >{{item.valorVenda.toLocaleString('pt-br',{style: 'currency', currency: 'BRL'})}}</p>
              </v-flex>
              <v-spacer></v-spacer>
              <v-flex sm2>
                <v-text-field label="Quantidade" v-model="item.quantidade" prefix="x" type="number"></v-text-field>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
        <v-divider></v-divider>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" dark @click="dialog = false, item.quantidade = 1">Cancelar</v-btn>
          <v-btn
            color="deep-orange accent-4"
            dark
            @click="addItensCarrinho()"
            v-if="stateUsuario.id !== undefined"
          >Adicionar ao carrinho</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-app>
</template>

<script>
import TradeDrawer from "../../components/trade/TradeDrawer.vue";
import axios from "axios";
import { mapMutations, mapState } from "vuex";

export default {
  data: () => ({
    dialog: false,
    search: "",
    qtdBadge: 0,
    index: 0,
    materiais: [],
    itens: [],
    usuario: {},
    item: {
      id: "",
      descricao: "",
      src: "",
      valorVenda: "",
      quantidade: 1,
      valorTotal: 0,
      material: 0
    }
  }),

  methods: {
    ...mapMutations(["getCarrinho"]),

    async getMateriais() {
      let materiais = await axios.get("/ConsultarMaterial?OPERACAO=CONSULTAR");
      this.materiais = materiais.data.entidades;
    },

    visualizar(index, id) {
      this.dialog = true;
      this.item.id = this.materiais[index].id;
      this.item.descricao = this.materiais[index].descricao;
      this.item.src = this.materiais[index].imagem;
      this.item.valorVenda = this.materiais[index].valorVenda;
      this.item.quantidade = 1;
      this.item.material = this.materiais[index].id;
      this.index = id;
    },

    addItensCarrinho() {
      let flag = false;
      let idItem = 0;
      if (this.itens.length != 0) {
        for (let i = 0; i < this.itens.length; i++) {
          if (this.itens[i].id == this.index) {
            flag = true;
            idItem = i;
          }
        }

        if (flag) {
          this.itens[idItem].quantidade += Number(this.item.quantidade);
        } else {
          this.itens.push({
            id: this.item.id,
            descricao: this.item.descricao,
            src: this.item.src,
            valorVenda: this.item.valorVenda,
            quantidade: this.item.quantidade,
            material: this.item.material,
            valorTotal: 0
          });
        }
      } else {
        this.itens.push({
          id: this.item.id,
          descricao: this.item.descricao,
          src: this.item.src,
          valorVenda: this.item.valorVenda,
          quantidade: this.item.quantidade,
          material: this.item.material,
          valorTotal: 0
        });
      }
      this.dialog = false;
    },

    deleteItemCarrinho(id) {
      this.itens = this.itens.filter(e => e.id != id);
    },

    irCarrinhoCompra() {
      this.itens.forEach(item => {
        item.valorTotal = item.valorVenda * item.quantidade;
      });
      this.getCarrinho(this.itens);
      this.$router.push({ name: "makingSale" });
    }
  },

  computed: {
    ...mapState(["stateItens", "stateUsuario"]),

    contadorItens: function() {
      let contador = 0;
      this.itens.forEach(item => {
        contador += Number(item.quantidade);
      });
      return contador;
    }
  },

  created() {
    this.itens = this.stateItens;
    this.getMateriais();
  },

  components: {
    TradeDrawer
  }
};
</script>
