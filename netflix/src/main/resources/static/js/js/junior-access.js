document.addEventListener("DOMContentLoaded", () => {
    const tableSelect = document.getElementById("tableSelect");
    const loadTableButton = document.getElementById("loadTable");
    const tableContainer = document.getElementById("tableContainer");

    // Hide payment option from Junior users
    if (tableSelect) {
        Array.from(tableSelect.options).forEach(option => {
            if (option.value === "payments") {
                option.style.display = "none";
            }
        });
    }

    loadTableButton.addEventListener("click", () => {
        const selectedTable = tableSelect.value;
        if (selectedTable !== "payments") { // Prevent loading payment table
            loadTableData(selectedTable);
        } else {
            alert("You do not have permission to access this data.");
        }
    });

    async function loadTableData(tableName) {
        const response = await fetch(`http://localhost:8081/api/${tableName}`, {
            method: "GET",
            headers: { "Authorization": `Bearer ${localStorage.getItem("token")}` }
        });
        const data = await response.json();
        renderTable(data, tableName);
    }

    function renderTable(data, tableName) {
        tableContainer.innerHTML = "";
        if (data.length === 0) {
            tableContainer.innerHTML = "<p>No data available.</p>";
            return;
        }

        const table = document.createElement("table");
        const headerRow = document.createElement("tr");

        // Filter out sensitive columns
        Object.keys(data[0]).forEach(key => {
            if (!["email", "password"].includes(key)) {
                const th = document.createElement("th");
                th.textContent = key;
                headerRow.appendChild(th);
            }
        });

        table.appendChild(headerRow);

        data.forEach(item => {
            const row = document.createElement("tr");
            Object.entries(item).forEach(([key, value]) => {
                if (!["email", "password"].includes(key)) {
                    const td = document.createElement("td");
                    td.textContent = value;
                    row.appendChild(td);
                }
            });
            table.appendChild(row);
        });

        tableContainer.appendChild(table);
    }
});
