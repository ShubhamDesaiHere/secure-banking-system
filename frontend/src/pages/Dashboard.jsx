import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function Dashboard() {

  const navigate = useNavigate();

  const [account, setAccount] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {

    const token = localStorage.getItem("token");

    if (!token) {
      navigate("/login");
      return;
    }

    const fetchMyAccount = async () => {

      try {

        const response = await axios.get(
          "/api/accounts/my-account",
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        console.log("MY ACCOUNT:", response.data);

        setAccount(response.data);

      } catch (error) {

        console.error("ACCOUNT ERROR:", error);

        if (error.response?.status === 401 ||
            error.response?.status === 403) {

          localStorage.removeItem("token");
          navigate("/login");

        } else {
          setError("Unable to load account details.");
        }

      } finally {
        setLoading(false);
      }
    };

    fetchMyAccount();

  }, [navigate]);


  const handleLogout = () => {

    localStorage.removeItem("token");

    navigate("/login");
  };


  if (loading) {
    return (
      <div className="container mt-5 text-center">
        <h3>Loading account...</h3>
      </div>
    );
  }


  return (
    <div className="container mt-5">

      <div className="d-flex justify-content-between align-items-center mb-4">

        <h2>Secure Banking System</h2>

        <button
          className="btn btn-danger"
          onClick={handleLogout}
        >
          Logout
        </button>

      </div>


      {error && (
        <div className="alert alert-danger">
          {error}
        </div>
      )}


      {account && (

        <div className="row">

          {/* Balance */}
          <div className="col-md-4 mb-3">

            <div className="card shadow">

              <div className="card-body">

                <h5 className="card-title">
                  Account Balance
                </h5>

                <h2 className="text-success">
                  ₹ {account.balance}
                </h2>

              </div>

            </div>

          </div>


          {/* Account Number */}
          <div className="col-md-4 mb-3">

            <div className="card shadow">

              <div className="card-body">

                <h5 className="card-title">
                  Account Number
                </h5>

                <h4>
                  {account.accountNumber}
                </h4>

              </div>

            </div>

          </div>


          {/* Account Type */}
          <div className="col-md-4 mb-3">

            <div className="card shadow">

              <div className="card-body">

                <h5 className="card-title">
                  Account Type
                </h5>

                <h4>
                  {account.accountType}
                </h4>

              </div>

            </div>

          </div>

        </div>

      )}

    </div>
  );
}

export default Dashboard;