hdocument.addEventListener("DOMContentLoaded", () => {
    const loadTableButton = document.getElementById("loadTable");
    const tableSelect = document.getElementById("tableSelect");
    const tableContainer = document.getElementById("tableContainer");
    const crudForm = document.getElementById("crudForm");
    const formFields = document.getElementById("formFields");

    if (!loadTableButton || !tableSelect || !tableContainer || !crudForm) {
        console.error("One or more required DOM elements are missing.");
        return;
    }

    loadTableButton.addEventListener("click", () => loadTableData(tableSelect.value));

    // Fetch data from the server
    async function fetchAPI(url, method, data = null) {
        const headers = {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${localStorage.getItem("token")}`
        };
        const response = await fetch(url, {
            method: method,
            headers: headers,
            body: data ? JSON.stringify(data) : null
        });
        if (!response.ok) throw new Error(`HTTP error! Status: ${response.status}`);
        return response.json();
    }

    async function loadTableData(tableName) {
        tableContainer.innerHTML = "<p>Loading...</p>";
        try {
            const data = await fetchAPI(`http://localhost:8081/api/${tableName}`, "GET");
            renderTable(data, tableName);
        } catch (error) {
            console.error("Error loading table data:", error);
            tableContainer.innerHTML = `<p>Error loading table data: ${error.message}</p>`;
        }
    }

    function renderTable(data, tableName) {
        tableContainer.innerHTML = "";
        if (!data || data.length === 0) {
            tableContainer.innerHTML = `<p>No data available for table "${tableName}".</p>`;
            return;
        }

        const table = document.createElement("table");
        const headerRow = document.createElement("tr");
        Object.keys(data[0]).forEach(key => headerRow.appendChild(createElement('th', key)));
        headerRow.appendChild(createElement('th', 'Actions'));
        table.appendChild(headerRow);

        data.forEach(item => {
            const tr = document.createElement("tr");
            Object.entries(item).forEach(([key, value]) => tr.appendChild(createElement('td', value)));
            const actionsTd = document.createElement("td");
            actionsTd.appendChild(createActionButton('Edit', () => loadEditForm(item, tableName)));
            actionsTd.appendChild(createActionButton('Delete', () => deleteRow(item.id, tableName)));
            tr.appendChild(actionsTd);
            table.appendChild(tr);
        });
        tableContainer.appendChild(table);
    }

    function createElement(tag, content) {
        const element = document.createElement(tag);
        element.textContent = content;
        return element;
    }

    function createActionButton(text, action) {
        const button = document.createElement("button");
        button.textContent = text;
        button.onclick = action;
        return button;
    }

    function loadEditForm(item, tableName) {
        crudForm.dataset.id = item.id; // Store the ID for use in PUT or PATCH requests
        crudForm.dataset.table = tableName; // Store the table name for use in requests
        formFields.innerHTML = ""; // Clear previous form fields
        Object.entries(item).forEach(([key, value]) => {
            const label = document.createElement('label');
            label.textContent = key;
            const input = document.createElement('input');
            input.name = key;
            input.value = value;
            formFields.appendChild(label);
            formFields.appendChild(input);
        });
    }

    crudForm.addEventListener("submit", async (e) => {
        e.preventDefault();
        const data = Object.fromEntries(new FormData(crudForm));
        const id = crudForm.dataset.id;
        const tableName = crudForm.dataset.table;
        const method = id ? 'PUT' : 'POST';
        const url = `http://localhost:8081/api/${tableName}/${id}`;

        try {
            const result = await fetchAPI(url, method, data);
            alert(`${id ? "Updated" : "Added"} successfully!`);
            crudForm.reset();
            delete crudForm.dataset.id; // Remove the stored ID after operation
            loadTableData(tableName);
        } catch (error) {
            console.error("Error saving entry:", error);
            alert(`Failed to save entry: ${error.message}`);
        }
    });

    async function deleteRow(id, tableName) {
        if (!confirm("Are you sure you want to delete this entry?")) return;

        try {
            await fetchAPI(`http://localhost:8081/api/${tableName}/${id}`, "DELETE");
            alert("Deleted successfully!");
            loadTableData(tableName);
        } catch (error) {
            console.error("Error deleting entry:", error);
            alert(`Failed to delete entry: ${error.message}`);
        }
    }
});
