package de.schneefisch.fruas.database;

import de.schneefisch.fruas.model.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    private DBConnector dbc;

    public ProductDAO() {
        try {
            this.dbc = new DBConnector();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int updateProduct(Product product) throws SQLException {
        String query = "update produkt set "
                + "nameProdukt = ?, "
                + "versionsnummerProdukt= ?, "
                + "preisProdukt = ?, "
                + "systemvoraussetzungProdukt = ?, "
                + "where idProdukt = ?;";
        PreparedStatement statement = dbc.getConnection().prepareStatement(query);
        statement.setString(1, product.getName());
        statement.setString(2, product.getVersion());
        statement.setFloat(3, product.getPrice());
        statement.setString(4, product.getRequirements());
        statement.setInt(5, product.getId());
        int updated = statement.executeUpdate();
        return updated;
    }

    public int deleteProduct(int productId) throws SQLException {
        String query = "delete from produkt where idProdukt = ?;";
        PreparedStatement statement = dbc.getConnection().prepareStatement(query);
        statement.setInt(1, productId);
        int removed = statement.executeUpdate();
        return removed;
    }

    public Product searchProductById(int productId) throws SQLException {
        String query = "select * from produkt where idProdukt = ?;";
        PreparedStatement statement = dbc.getConnection().prepareStatement(query);
        statement.setInt(1, productId);
        ResultSet rs = statement.executeQuery();
        Product product = new Product();
        while(rs.next()) {
            product =  new Product(rs.getInt("idProdukt"), rs.getString("nameProdukt"), rs.getString("versionsnummerProdukt"),
                    rs.getFloat("preisProdukt"), rs.getString("systemvoraussetzungProdukt"));
        }
        return product;
    }


    public List<Product> searchProductByName(String name) throws SQLException {
        List<Product> productList = new ArrayList<Product>();
        String query = "select * from produkt where nameProdukt like ?;";
        PreparedStatement statement = dbc.getConnection().prepareStatement(query);
        statement.setString(1, "%" + name + "%");
        ResultSet rs = statement.executeQuery();
        while(rs.next()) {
            Product product = new Product(rs.getInt("idProdukt"), rs.getString("nameProdukt"), rs.getString("versionsnummerProdukt"),
                    rs.getFloat("preisProdukt"), rs.getString("systemvoraussetzungProdukt"));
            productList.add(product);
        }
        return productList;
    }

    public List<Product> selectAllProducts() throws SQLException {
        List<Product> productList = new ArrayList<Product>();
        String query = "select * from produkt;";
        PreparedStatement statement = dbc.getConnection().prepareStatement(query);
        ResultSet rs = statement.executeQuery();
        while(rs.next()) {
            Product product = new Product(rs.getInt("idProdukt"), rs.getString("nameProdukt"), rs.getString("versionsnummerProdukt"),
                    rs.getFloat("preisProdukt"), rs.getString("systemvoraussetzungProdukt"));
            productList.add(product);
        }
        return productList;
    }

    public Product insertProduct(Product product) throws SQLException {

        String query = "insert into produkt (nameProdukt, versionsnummerProdukt, preisProdukt, "
                + "systemvoraussetzungProdukt)" + " values(?, ?, ?, ?);";

        PreparedStatement statement = dbc.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, product.getName());
        statement.setString(2, product.getVersion());
        statement.setFloat(3, product.getPrice());
        statement.setString(4, product.getRequirements());

        int results = statement.executeUpdate();
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                product.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Creating product failed, no ID obtained.");
            }
        }
        System.out.println("inserted " + results +" product:" + product);
        return product;
    }


}
