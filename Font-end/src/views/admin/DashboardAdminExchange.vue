
<template>
  <v-app>
    <!-- Toolbar -->
    <AdminToolbar />

    <!-- Drawer -->
    <AdminDrawer />

    <!-- Conteudo -->
    <v-card flat>
      <v-card-title>
        <span>Trocas</span>
        <v-spacer></v-spacer>
        <v-text-field
          v-model="search"
          append-icon="search"
          label="Pesquisar"
          single-line
          hide-details
          data-cy="pesquisar"
        ></v-text-field>
      </v-card-title>
      <v-data-table
        :headers="headers"
        :items="trocas"
        :search="search"
        :hide-default-footer="true"
        fixed-header
        height="490px"
        items-per-page="10000"
      >
        <template v-slot:item.visualizarTroca="{item}">
          <v-icon @click="visualizarTroca(item)">edit</v-icon>
        </template>
      </v-data-table>
    </v-card>

    <!-- Modal visualizar -->
    <v-dialog v-model="dialog" persistent max-width="625">
      <v-card>
        <v-card-title class="headline">
          <v-toolbar dark color="deep-orange accent-4">
            <v-toolbar-title class="headline text-uppercase">Item</v-toolbar-title>
          </v-toolbar>
        </v-card-title>
        <v-card-text>
          <v-container grid-list-md>
            <v-layout wrap>
              <v-flex sm10>
                <v-text-field label="Descrição" v-model="troca.descricao" readonly></v-text-field>
              </v-flex>
              <v-flex sm2>
                <v-text-field label="Quantidade" v-model="troca.quantidade" readonly></v-text-field>
              </v-flex>
              <v-flex sm4>
                <v-text-field label="Valor" v-model="troca.valorVenda" readonly></v-text-field>
              </v-flex>
              <v-flex sm4>
                <v-text-field label="Valor total" v-model="troca.valorTotal" readonly></v-text-field>
              </v-flex>
              <v-flex sm4 data-cy="situacao">
                <v-select
                  :items="['Em troca', 'Troca autorizada', 'Troca negada']"
                  label="Situação"
                  v-model="troca.situacao"
                ></v-select>
              </v-flex>
              <v-flex sm12>
                <v-text-field label="Motivo" v-model="troca.motivo" readonly></v-text-field>
              </v-flex>
              <v-flex sm12>
                <v-textarea label="Descrição" v-model="troca.descricaoMotivo" readonly></v-textarea>
              </v-flex>
            </v-layout>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="dialog = false">Cancelar</v-btn>
          <v-btn color="green darken-1" text @click="alterar()">confirmar</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Footer -->
    <AdminFooter />
  </v-app>
</template>

<script>
import AdminToolbar from "../../components/admin/AdminToolbar.vue";
import AdminDrawer from "../../components/admin/AdminDrawer.vue";
import AdminFooter from "../../components/admin/AdminFooter.vue";
import axios from "axios";
import qs from "querystring";

export default {
  data() {
    return {
      dialog: false,
      search: "",
      motivo: "",
      headers: [
        {
          text: "Descrição",
          align: "left",
          sortable: true,
          value: "descricao"
        },
        { text: "Valor", value: "valorVenda" },
        { text: "Quantidade", value: "quantidade" },
        { text: "Valor total", value: "valorTotal" },
        { text: "Situação", value: "situacao" },
        { text: "Visualizar", value: "visualizarTroca", sortable: false }
      ],
      trocas: [],
      troca: {},
      idCupom: 0
    };
  },

  methods: {
    async listar() {
      this.trocas = [];
      let dadosCompra = await axios.get("/ConsultarVenda?OPERACAO=CONSULTAR");
      dadosCompra.data.entidades.forEach(venda => {
        this.trocas = this.trocas.concat(
          venda.itens.filter(item => item.papel == "Troca")
        );
      });
    },

    async alterar() {
      await axios
        .post("/AlterarItem?OPERACAO=ALTERAR", qs.stringify(this.troca))
        .then(function(response) {
          console.log();
        })
        .catch(function(error) {
          console.log(error);
        });

      let cupomTroca = {
        valor: this.troca.valorTotal,
        usuario: this.troca.usuario,
        material: this.troca.material
      };

      let id = 0;
      if (this.troca.situacao == "Troca autorizada") {
        await axios
          .post("/SalvarCupomTroca?OPERACAO=SALVAR", qs.stringify(cupomTroca))
          .then(function(response) {
            id = response.data.entidades[0].id;
          })
          .catch(function(error) {
            console.log(error);
          });
      } else if (this.troca.situacao == "Em troca") {
        await axios
          .post(`/ExcluirCupomTroca?OPERACAO=EXCLUIR&id=${this.idCupom}`)
          .then(function(response) {
            console.log();
          })
          .catch(function(error) {
            console.log(error);
          });
      }

      this.idCupom = id;
      this.dialog = false;
      this.motivo = "";
      this.listar();
    },

    visualizarTroca(item) {
      this.troca = {
        id: item.id,
        venda: item.venda,
        usuario: item.usuario,
        material: item.material,
        descricao: item.descricao,
        quantidade: item.quantidade,
        valorVenda: item.valorVenda,
        valorTotal: item.valorTotal,
        situacao: item.situacao,
        motivo: item.motivo,
        descricaoMotivo: item.descricaoMotivo
      };
      this.dialog = true;
    }
  },

  created() {
    this.listar();
  },

  components: {
    AdminToolbar,
    AdminDrawer,
    AdminFooter
  }
};
</script>
