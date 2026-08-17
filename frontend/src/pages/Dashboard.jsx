function Dashboard() {
  const token = localStorage.getItem("token");

  return (
    <div className="container mt-5">
      <h1>Customer Dashboard</h1>

      <p className="mt-3">
        Login successful!
      </p>

      <p>
        JWT stored: {token ? "Yes" : "No"}
      </p>
    </div>
  );
}

export default Dashboard;