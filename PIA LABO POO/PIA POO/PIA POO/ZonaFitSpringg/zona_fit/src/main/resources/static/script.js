document.addEventListener('DOMContentLoaded', async function () {
    const modal = document.getElementById('modal-cliente');
    const btnAbrir = document.getElementById('btn-abrir-modal');
    const btnCancelar = document.getElementById('btn-cancelar'); // Referencia a cancelar
    const btnGuardar = document.getElementById('btn-guardar');   // Referencia a guardar
    const tituloModal = document.getElementById('modal-titulo');
    let modoEdicion = false;
    let idClienteEditar = null;

    // Abrir modal para nuevo cliente
    btnAbrir.addEventListener('click', (e) => {
        e.preventDefault();
        modoEdicion = false;
        tituloModal.textContent = "Nuevo Cliente";
        modal.style.display = 'flex';
    });

    // Cancelar y cerrar modal
    btnCancelar.addEventListener('click', () => {
        modal.style.display = 'none';
    });

    // Guardar cliente (cuando se presiona el botón "Guardar")
    btnGuardar.addEventListener('click', async () => {
        const nombre = document.getElementById('nombre').value.trim();
        const apellido = document.getElementById('apellido').value.trim();
        const membresia = document.getElementById('membresia').value.trim();

        if (!nombre || !apellido || !membresia) {
            alert("Todos los campos deben estar llenos.");
            return;
        }

        const clienteData = { nombre, apellido, membresia };

        try {
            let response;
            if (modoEdicion) {
                response = await fetch(`/api/clientes/${idClienteEditar}`, {
                    method: 'PUT',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(clienteData)
                });
            } else {
                response = await fetch('/api/clientes', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(clienteData)
                });
            }

            if (response.ok) {
                alert(modoEdicion ? "Cliente actualizado correctamente." : "Cliente agregado correctamente.");
                modal.style.display = 'none';
                await cargarClientes();
                modoEdicion = false;
                idClienteEditar = null;
            } else {
                alert("Error al guardar el cliente.");
            }
        } catch (error) {
            console.error("Error:", error);
        }
    });

    // Cargar los clientes de la API
    async function cargarClientes() {
        try {
            const response = await fetch('/api/clientes');
            const clientes = await response.json();
            const tbody = document.querySelector('tbody');
            tbody.innerHTML = '';

            clientes.forEach(cliente => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${cliente.id}</td>
                    <td>${cliente.nombre}</td>
                    <td>${cliente.apellido}</td>
                    <td>${cliente.membresia}</td>
                    <td>
                        <button onclick="editar(${cliente.id})">Editar</button>
                        <button onclick="eliminar(${cliente.id})">Eliminar</button>
                    </td>
                `;
                tbody.appendChild(row);
            });
        } catch (error) {
            console.error('Error al cargar los clientes:', error);
        }
    }

    // Función para editar un cliente
    window.editar = async function (id) {
        try {
            const response = await fetch(`/api/clientes/${id}`);
            const cliente = await response.json();

            document.getElementById('nombre').value = cliente.nombre;
            document.getElementById('apellido').value = cliente.apellido;
            document.getElementById('membresia').value = cliente.membresia;

            modoEdicion = true;
            idClienteEditar = cliente.id;
            tituloModal.textContent = "Actualizar Cliente";
            modal.style.display = 'flex';
        } catch (error) {
            alert("Error al obtener datos del cliente.");
        }
    };

    // Función para eliminar un cliente
    window.eliminar = async function (id) {
        const confirmacion = confirm("¿Estás seguro de que deseas eliminar este cliente?");
        if (!confirmacion) return;

        try {
            const response = await fetch(`/api/clientes/${id}`, {
                method: 'DELETE'
            });

            if (response.ok) {
                alert("Cliente eliminado correctamente.");
                await cargarClientes();
            } else {
                alert("Error al eliminar el cliente.");
            }
        } catch (error) {
            alert("Error en la solicitud de eliminación.");
        }
    };

    // Cargar los clientes al iniciar la página
    await cargarClientes();

    // Refrescar los clientes al hacer clic en 'Inicio'
    document.getElementById('inicio-btn').addEventListener('click', async function (event) {
        event.preventDefault();
        try {
            const response = await fetch('/api/refrescarClientes');
            const mensaje = await response.text();
            alert(mensaje);
            await cargarClientes();
        } catch (error) {
            alert("Error al refrescar los clientes.");
        }
    });
});
