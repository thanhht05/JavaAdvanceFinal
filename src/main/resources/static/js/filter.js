document.addEventListener("DOMContentLoaded", function () {
  /* ==============================
     1. CLEAN URL: xóa dấu ? cuối
     ============================== */
  const currentUrl = new URL(window.location.href);
  if (currentUrl.search === "") {
    window.history.replaceState({}, document.title, currentUrl.pathname);
  }

  const params = new URLSearchParams(window.location.search);

  /* ==============================
     2. RESTORE FILTER STATE
     ============================== */

  // PRICE RANGE
  const priceRange = params.get("priceRange");
  if (priceRange !== null) {
    document.querySelectorAll("input[name='priceRange']").forEach((el) => {
      el.checked = el.value === priceRange;
    });
  }

  // CAPACITY
  const capacity = params.get("capacity");
  if (capacity !== null) {
    document.querySelectorAll("input[name='capacity']").forEach((el) => {
      el.checked = el.value === capacity;
    });
  }

  // TYPE (radio hoặc checkbox đều dùng được)
  const types = params.getAll("type");
  if (types.length > 0) {
    document.querySelectorAll("input[name='type']").forEach((el) => {
      el.checked = types.includes(el.value);
    });
  }

  /* ==============================
     3. KEEP FILTER WHEN PAGINATE
     ============================== */
  document.querySelectorAll(".page-link-js").forEach((link) => {
    const url = new URL(link.href);

    params.forEach((value, key) => {
      if (key !== "page") {
        url.searchParams.set(key, value);
      }
    });

    link.href = url.pathname + "?" + url.searchParams.toString();
  });

  /* ==============================
     4. REMOVE FILTER WHEN SELECT "ALL"
     ============================== */
  const form = document.getElementById("filterForm");
  if (!form) return;

  form.addEventListener("submit", function () {
    const filterNames = ["priceRange", "type", "capacity"];

    filterNames.forEach((name) => {
      const allOption = document.querySelector(
        `input[name='${name}'][value='']`
      );
      if (allOption && allOption.checked) {
        allOption.disabled = true; // không gửi param đó
      }
    });
  });
});
