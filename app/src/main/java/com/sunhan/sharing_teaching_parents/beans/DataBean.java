package com.sunhan.sharing_teaching_parents.beans;

import java.util.ArrayList;
import java.util.List;

import fj.dropdownmenu.lib.pojo.DropdownItemObject;

/**
 * @author FengTong
 * @date 2017/8/22
 */
public class DataBean {

    //高校分类列表数据
    private final static String mUniversityArray[] = {"重庆大学", "重庆交通大学", "西南大学", "重庆邮电大学", "西南政法大学"};
    //综合排序
    private final static String mMultipleArray[] = {"距离最近", "口碑排序", "热度排序"};

    /**
     * 获取分类数据
     * @return itemType
     */
    public static List<DropdownItemObject> getType(){
        List<DropdownItemObject> itemType = new ArrayList<>();//分类
        DropdownItemObject typeObj = new DropdownItemObject(-1, "全部高校", "全部高校");
        itemType.add(typeObj);
        for (int i = 0; i < 5; i++) {
            //typeObj = new DropdownItemObject(i, "分类" + i, "分类" + i);
            typeObj = new DropdownItemObject(i, mUniversityArray[i], "分类" + i);
            itemType.add(typeObj);
        }
        return itemType;
    }

    /**
     * 获取动物一级数据
     * @return itemType
     */
    public static List<DropdownItemObject> getAnimalSingle(){
        List<DropdownItemObject> itemAnimal = new ArrayList<>();//动物
        DropdownItemObject animalObj = new DropdownItemObject(-1, "年级科目", "全部动物");
        itemAnimal.add(animalObj);
        animalObj = new DropdownItemObject(1, "狗", "狗");
        itemAnimal.add(animalObj);
        animalObj = new DropdownItemObject(2, "猫", "猫");
        itemAnimal.add(animalObj);
        return itemAnimal;
    }

    /**
     * 获取动物二级数据
     * @return itemType
     */
    public static List<DropdownItemObject> getAnimalDouble(){
        List<DropdownItemObject> itemAnimalDouble = new ArrayList<>();//所有动物子分类
        DropdownItemObject animalObj = new DropdownItemObject(-1, -1, "全部动物", "全部动物");
        itemAnimalDouble.add(animalObj);

        animalObj = new DropdownItemObject(1, -1, "全部狗", "全部狗");
        itemAnimalDouble.add(animalObj);
        animalObj = new DropdownItemObject(1, 1, "藏獒", "藏獒");
        itemAnimalDouble.add(animalObj);
        animalObj = new DropdownItemObject(1, 2, "二哈", "二哈");
        itemAnimalDouble.add(animalObj);
        animalObj = new DropdownItemObject(1, 3, "土狗", "土狗");
        itemAnimalDouble.add(animalObj);

        animalObj = new DropdownItemObject(2, -1, "全部猫", "全部猫");
        itemAnimalDouble.add(animalObj);
        animalObj = new DropdownItemObject(2, 1, "暹罗", "暹罗");
        itemAnimalDouble.add(animalObj);
        animalObj = new DropdownItemObject(2, 2, "波斯", "波斯");
        itemAnimalDouble.add(animalObj);
        return itemAnimalDouble;
    }


    /**
     * 获取综合排序
     * @return itemType
     */
    public static List<DropdownItemObject> getMultiple(){
        List<DropdownItemObject> itemType = new ArrayList<>();//综合排序
        DropdownItemObject typeObj = new DropdownItemObject(-1, "综合排序", "综合排序");
        itemType.add(typeObj);
        for (int i = 0; i < 3; i++) {
            typeObj = new DropdownItemObject(i, mMultipleArray[i], "综合排序" + i);
            itemType.add(typeObj);
        }
        return itemType;
    }
}
