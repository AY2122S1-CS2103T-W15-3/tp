const tables = document.getElementsByClassName("example-table");
for (const table of tables) {
    const rows = table.getElementsByTagName("tbody")[0].rows;
    for (let i = 2; i < rows.length; i += 3) {
        const row = rows[i];
        row.removeChild(row.lastElementChild);
        row.firstElementChild.setAttribute("colspan", "2");
    }
}