package hu.nje.javagyakorlatbeadando.dto;

import java.math.BigDecimal;

public class ProductDTO {
    private Long aruKod;
    private String nev;
    private String katNev;
    private String egyseg;
    private BigDecimal ar;
    private BigDecimal mennyiseg;
    private BigDecimal osszeg;
    private String productPic;

    public Long getAruKod() { return aruKod; }
    public void setAruKod(Long aruKod) { this.aruKod = aruKod; }

    public String getNev() { return nev; }
    public void setNev(String nev) { this.nev = nev; }

    public String getKatNev() { return katNev; }
    public void setKatNev(String katNev) { this.katNev = katNev; }

    public String getEgyseg() { return egyseg; }
    public void setEgyseg(String egyseg) { this.egyseg = egyseg; }

    public BigDecimal getAr() { return ar; }
    public void setAr(BigDecimal ar) { this.ar = ar; }

    public BigDecimal getMennyiseg() { return mennyiseg; }
    public void setMennyiseg(BigDecimal mennyiseg) { this.mennyiseg = mennyiseg; }

    public BigDecimal getOsszeg() { return osszeg; }
    public void setOsszeg(BigDecimal osszeg) { this.osszeg = osszeg; }

    public String getProductPic() { return productPic; }
    public void setProductPic(String productPic) { this.productPic = productPic; }
}
