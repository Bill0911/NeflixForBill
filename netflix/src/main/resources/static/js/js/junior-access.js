const apiBaseUrl = "http://localhost:8081/api"; // API Base URL
const token = localStorage.getItem("token"); // JWT token from login

// Fetch all tables and display them
function fetchTableData(tableName) {
    fetch(`${apiBaseUrl}/${tableName}`, {
        headers: { Authorization: `Bearer ${token}` },
    })
        .then(response => response.json())
        .then(data => displayTable(tableName, data))
        .catch(err => console.error(`Error fetching ${tableName}:`, err));
}

// Display table
function displayTable(tableName, data) {
    const contentDiv = document.getElementById("content");

    const table = document.createElement("table");
    const thead = document.createElement("thead");
    const tbody = document.createElement("tbody");

    // Table header
    const headers = Object.keys(data[0] || {}).concat("Actions");
    const headerRow = document.createElement("tr");
    headers.forEach(header => {
        const th = document.createElement("th");
        th.textContent = header;
        headerRow.appendChild(th);
    });
    thead.appendChild(headerRow);

    // Table body
    data.forEach(row => {
        const rowElement = document.createElement("tr");
        Object.values(row).forEach(value => {
            const td = document.createElement("td");
            td.textContent = value;
            rowElement.appendChild(td);
        });

        // Actions column
        const actionTd = document.createElement("td");
        actionTd.className = "actions";

        const deleteButton = document.createElement("button");
        deleteButton.textContent = "Delete";
        deleteButton.onclick = () => deleteRow(tableName, row.id);

        const editButton = document.createElement("button");
        editButton.textContent = "Edit";
        editButton.onclick = () => editRow(tableName, row);

        actionTd.appendChild(deleteButton);
        actionTd.appendChild(editButton);
        rowElement.appendChild(actionTd);

        tbody.appendChild(rowElement);
    });

    table.appendChild(thead);
    table.appendChild(tbody);
    contentDiv.innerHTML = ""; // Clear existing content
    contentDiv.appendChild(table);
}

// Delete a row
function deleteRow(tableName, id) {
    fetch(`${apiBaseUrl}/${tableName}/${id}`, {
        method: "DELETE",
        headers: { Authorization: `Bearer ${token}` },
    })
        .then(response => {
            if (response.ok) {
                alert("Row deleted successfully!");
                fetchTableData(tableName);
            } else {
                alert("Failed to delete row.");
            }
        })
        .catch(err => console.error("Error deleting row:", err));
}

// Edit a row
function editRow(tableName, row) {
    const newValue = prompt("Enter new value for the row:", JSON.stringify(row));
    if (newValue) {
        fetch(`${apiBaseUrl}/${tableName}/${row.id}`, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            },
            body: newValue,
        })
            .then(response => {
                if (response.ok) {
                    alert("Row updated successfully!");
                    fetchTableData(tableName);
                } else {
                    alert("Failed to update row.");
                }
            })
            .catch(err => console.error("Error updating row:", err));
    }
}

// Fetch genres table as default
fetchTableData("genres");
