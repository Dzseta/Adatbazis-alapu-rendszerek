package hu.adatb.view;

import hu.adatb.controller.*;
import hu.adatb.model.Coupon;
import hu.adatb.model.Order;
import hu.adatb.model.Shop;
import hu.adatb.model.User;
import hu.adatb.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class CheckoutDialog extends Stage {
    private OrderController orderController;
    private ShopController shopController;
    private SessionController sessionController;
    private CouponController couponController;
    private GenreController genreController;
    private ShoppingCart shoppingCart;

    private List<Order> orders;

    private BorderPane contents;

    public CheckoutDialog(OrderController orderController, ShopController shopController, CouponController couponController, GenreController genreController,  List<Order> orders) {
        this.orderController = orderController;
        this.shopController = shopController;
        this.couponController = couponController;
        this.genreController = genreController;
        this.orders = orders;
        sessionController = SessionController.getInstance();
        shoppingCart = ShoppingCart.getInstance();
        construct();
    }

    private void construct(){
        contents = new BorderPane();

        GridPane checkoutGrid = new GridPane();
        checkoutGrid.setVgap(10);
        checkoutGrid.setHgap(10);
        checkoutGrid.setPadding(new Insets(10));

        CheckBox homeDelivery = new CheckBox("Házhozszállítás");
        ComboBox<String> shopBox = new ComboBox<>(listShops());
        TextField couponField = new TextField();

        checkoutGrid.add(new Text("Átvétel helyszíne: "), 0, 0, 2, 1);
        checkoutGrid.add(homeDelivery, 0, 1);
        checkoutGrid.add(shopBox, 1, 1);
        checkoutGrid.add(new Text("Kupon:"), 0, 2);
        checkoutGrid.add(couponField, 1, 2);

        GridPane orderGrid = new GridPane();
        orderGrid.setVgap(10);
        orderGrid.setHgap(10);
        orderGrid.setPadding(new Insets(10));

        int index = 0;

        for(Order o: orders){
            orderGrid.add(new Text(o.getIsbn() + " x "), 0, index);
            orderGrid.add(new Text(o.getQuantity() + " db"), 1, index);
            orderGrid.add(new Text(o.getSubtotal() + " Ft"), 2, index);

            index++;
        }

        Button orderButton = new Button("Megrendel");
        orderButton.setOnAction(e -> {

            if(shopBox.getSelectionModel().isEmpty()){
                Utils.showWarning("Kérem válassza ki az áruházat!");
                return;
            }

            if(!couponField.getText().isEmpty()){
                Coupon coupon = couponController.getSelectedCoupon(couponField.getText());
                if(coupon == null){
                    Utils.showWarning("Érvénytelen kuponkód!");
                    return;
                }

                for(int i=0; i<orders.size(); i++){
                    if(genreController.getSelectedGenre(orders.get(i).getIsbn()).contains(coupon.getGenre())){
                        orders.get(i).setSubtotal(orders.get(i).getSubtotal() * ((100 - coupon.getDiscount()) / 100));
                    }
                }
            }

            User user = sessionController.getUser();
            Shop shop = shopController.getSelectedShop(shopBox.getSelectionModel().getSelectedItem());

            String location = "";

            if(homeDelivery.isSelected()){
                location = user.getIrsz() + " " + user.getCity() + " " + user.getStreet() + " " + user.getHouse();
            } else {
                location = shop.getZipcode() + " " + shop.getCity() + " " + shop.getStreet() + " " + shop.getHnumber();
            }

            for(int i=0; i<orders.size(); i++){
                orders.get(i).setLocation(location);
            }

            for(int i=0; i<orders.size(); i++){
                if (!orderController.quantityNumber(orders.get(i), shop.getId())) {
                    Utils.showWarning("Nincs elég könyv a raktárban!");
                    return;
                }

            }

            for(int i=0; i<orders.size(); i++){
                if(!orderController.add(orders.get(i))){
                    Utils.showWarning("Nem sikerült a rendelés!");
                    return;
                } else {
                    shoppingCart.clearContents();
                    close();
                }
            }


        });
        Button cancelButton = new Button("Mégse");
        cancelButton.setOnAction(e -> close());

        FlowPane buttonPane = new FlowPane();
        buttonPane.setOrientation(Orientation.HORIZONTAL);
        buttonPane.setHgap(15);
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.getChildren().addAll(orderButton, cancelButton);

        contents.setTop(checkoutGrid);
        contents.setCenter(orderGrid);
        contents.setBottom(buttonPane);
        BorderPane.setAlignment(buttonPane, Pos.CENTER);

        Scene scene = new Scene(contents);
        setScene(scene);
        setTitle("Rendelés");
        show();
    }

    private ObservableList<String> listShops(){
        ObservableList<String> list = null;

        List<Shop> shops = shopController.list();
        List<String> shopNames = new ArrayList<>();

        for(Shop s: shops){
            shopNames.add(s.getName());
        }

        list = FXCollections.observableList(shopNames);

        return list;
    }
}
