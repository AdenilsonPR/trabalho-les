export function formatteDate(data) {
    let vetData = data.split(" ");
    let newFormatte = `${vetData[1]} ${vetData[2]} ${vetData[5]}`;
    let d = new Date(newFormatte);
    return [d.getDate(), d.getMonth() + 1, d.getFullYear()]
        .map(n => (n < 10 ? `0${n}` : `${n}`))
        .join("/");
}

export function formatteMoney(real) {
    return real.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
}

export function moneyToFloat(money) {
    return Number(money.replace("R$", "").replace(".", "").replace(",", "."));
}


