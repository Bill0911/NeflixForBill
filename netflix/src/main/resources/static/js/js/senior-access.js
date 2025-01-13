document.addEventListener("DOMContentLoaded", () => {
    const loadTableButton = document.getElementById("loadTable");
    const tableSelect = document.getElementById("tableSelect");
    const tableContainer = document.getElementById("tableContainer");
    const crudForm = document.getElementById("crudForm");
    const formFields = document.getElementById("formFields");

    if (!loadTableButton || !tableSelect || !tableContainer || !crudForm) {
        console.error("One or more required DOM elements are missing.");
        return;
    }

    let currentTable = "languages"; // Default table for CRUD operations

    // Load table on button click
    loadTableButton.addEventListener("click", async () => {
        currentTable = tableSelect.value; // Update current table
        await loadTableData(currentTable);
    });

    // Load table data dynamically
    async function loadTableData(tableName) {
        tableContainer.innerHTML = "<p>Loading...</p>";
        try {
            const response = await fetch(`http://localhost:8081/api/${tableName}`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                },
            });

            if (!response.ok) {
                throw new Error(`Failed to fetch data for table "${tableName}". Status: ${response.status}`);
            }

            const data = await response.json();
            renderTable(data, tableName);
        } catch (error) {
            console.error("Error loading table data:", error);
            tableContainer.innerHTML = `<p>Error loading table data: ${error.message}</p>`;
        }
    }

    // Render table dynamically
    function renderTable(data, tableName) {
        tableContainer.innerHTML = "";

        if (!data || data.length === 0) {
            tableContainer.innerHTML = `<p>No data available for table "${tableName}".</p>`;
            return;
        }

        const table = document.createElement("table");
        const headerRow = document.createElement("tr");

        // Generate table headers dynamically
        Object.keys(data[0]).forEach((key) => {
            const th = document.createElement("th");
            th.textContent = key;
            headerRow.appendChild(th);
        });

        // Add Actions column
        const actionsTh = document.createElement("th");
        actionsTh.textContent = "Actions";
        headerRow.appendChild(actionsTh);
        table.appendChild(headerRow);

        // Generate table rows dynamically
        data.forEach((row) => {
            const tr = document.createElement("tr");

            Object.values(row).forEach((value) => {
                const td = document.createElement("td");
                td.textContent = value;
                tr.appendChild(td);
            });

            // Add action buttons
            const actionsTd = document.createElement("td");
            const editButton = document.createElement("button");
            editButton.textContent = "Edit";
            editButton.addEventListener("click", () => loadEditForm(row, tableName));

            const deleteButton = document.createElement("button");
            deleteButton.textContent = "Delete";
            deleteButton.addEventListener("click", () => deleteRow(row.id, tableName));

            actionsTd.appendChild(editButton);
            actionsTd.appendChild(deleteButton);
            tr.appendChild(actionsTd);

            table.appendChild(tr);
        });

        tableContainer.appendChild(table);
    }

    // Load form for adding/editing entries
    function loadEditForm(row = null, tableName) {
        crudForm.dataset.id = row ? row.id : ""; // Set ID for edit, or clear for add
        crudForm.dataset.table = tableName; // Store table name in form
        formFields.innerHTML = "";

        if (row) {
            Object.entries(row).forEach(([key, value]) => {
                if (key === "id") return; // Skip ID for editing

                const label = document.createElement("label");
                label.textContent = key;
                const input = document.createElement("input");
                input.name = key;
                input.value = value;
                formFields.appendChild(label);
                formFields.appendChild(input);
            });
        } else {
            // Add generic fields for adding new entries
            const label = document.createElement("label");
            label.textContent = "Enter details:";
            const input = document.createElement("input");
            input.name = "name"; // Default field (can be adjusted)
            formFields.appendChild(label);
            formFields.appendChild(input);
        }
    }

    // Handle form submission (Add/Edit)
    crudForm.addEventListener("submit", async (e) => {
        e.preventDefault();
        const id = crudForm.dataset.id; // ID for editing
        const tableName = crudForm.dataset.table; // Current table
        const formData = new FormData(crudForm);
        const data = Object.fromEntries(formData.entries());
        const method = id ? "PUT" : "POST";
        const endpoint = id
            ? `http://localhost:8081/api/${tableName}/${id}`
            : `http://localhost:8081/api/${tableName}`;

        try {
            const response = await fetch(endpoint, {
                method,
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                },
                body: JSON.stringify(data),
            });

            if (!response.ok) {
                throw new Error(`Failed to ${id ? "update" : "add"} entry. Status: ${response.status}`);
            }

            alert(`${id ? "Updated" : "Added"} successfully!`);
            crudForm.reset();
            await loadTableData(tableName); // Reload table
        } catch (error) {
            console.error("Error saving entry:", error);
            alert("Failed to save entry. Check the console for details.");
        }
    });

    // Delete a row
    async function deleteRow(id, tableName) {
        if (!confirm("Are you sure you want to delete this entry?")) return;

        try {
            const response = await fetch(`http://localhost:8081/api/${tableName}/${id}`, {
                method: "DELETE",
                headers: {
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                },
            });

            if (!response.ok) {
                throw new Error(`Failed to delete entry. Status: ${response.status}`);
            }

            alert("Deleted successfully!");
            await loadTableData(tableName); // Reload table
        } catch (error) {
            console.error("Error deleting entry:", error);
            alert("Failed to delete entry. Check the console for details.");
        }
    }
});
