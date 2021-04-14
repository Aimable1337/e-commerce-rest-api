package com.spring.rest.ecommerce.DTO.Product;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = ProductViewDTO.Builder.class)
public class ProductViewDTO {

      private final long productId;

      private final String productName;

      private final int productPrice;

      private final String productCategory;

     private ProductViewDTO(final Builder builder){
         this.productId = builder.productId;
         this.productName = builder.productName;
         this.productPrice = builder.productPrice;
         this.productCategory = builder.productCategory;
     }

     public static Builder builder(){
         return new Builder();
     }

     public Builder toBuilder(){
         return builder()
                 .withProductId(productId)
                 .withProductName(productName)
                 .withProductPrice(productPrice)
                 .withProductCategory(productCategory);
     }

     public String getProductName() {
        return productName;
    }

     public int getProductPrice() {
        return productPrice;
    }

     public long getProductId() {
        return productId;
    }

     public String getProductCategory() {
        return productCategory;
    }

    @JsonPOJOBuilder
     public static class Builder{

         private long productId;

         private String productName;

         private int productPrice;

         private String productCategory;

         private Builder(){}

         public ProductViewDTO build(){
             return new ProductViewDTO(this);
         }

         public Builder withProductId(long productId) {
             this.productId = productId;
             return this;
         }

         public Builder withProductName(String productName) {
             this.productName = productName;
             return this;
         }

         public Builder withProductPrice(int productPrice) {
             this.productPrice = productPrice;
             return this;
         }

         public Builder withProductCategory(String productCategory) {
             this.productCategory = productCategory;
             return this;
         }

    }

}
