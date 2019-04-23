package lz.com.tools.city;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * -----------作者----------日期----------变更内容-----
 * -          刘泽      2019-03-15      城市数据实体
 */

public class CityPickerInfo implements Serializable {

    //显示名字
    private String name;
    private String id;// 地区id
    private long parentId;//父类id
    //下级数据集合
    private List<CityPickerInfo> city = new ArrayList<>();

    public String getName() {
        return name;
    }

    public CityPickerInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getId() {
        return id;
    }

    public CityPickerInfo setId(String id) {
        this.id = id;
        return this;
    }

    public long getParentId() {
        return parentId;
    }

    public CityPickerInfo setParentId(long parentId) {
        this.parentId = parentId;
        return this;
    }


    public List<CityPickerInfo> getCity() {
        return city;
    }

    public CityPickerInfo setCity(List<CityPickerInfo> city) {
        this.city = city;
        return this;
    }

    public CityPickerInfo addCity(CityPickerInfo city) {
        if (this.city == null) {
            this.city = new ArrayList<>();
        }
        this.city.add(city);
        return this;
    }


}
