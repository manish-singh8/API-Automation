package pojo;

import java.util.List;


public class postRequestBody {

    private  String name;
    private  String job;
    private List<String> language;

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    private String updatedAt;



    public List<cityRequestBody> getCity() {
        return cityRequestBody;
    }

    public void setCity(List<cityRequestBody> cityRequestBody) {
        this.cityRequestBody = cityRequestBody;
    }

    private List<cityRequestBody> cityRequestBody;





    public List<String> getLanguage() {
        return language;
    }

    public void setLanguage(List<String> language) {
        this.language = language;
    }



    public void setName(String name)
    {
        this.name=name;

    }
    public void setJob(String job)
    {
         this.job=job;
    }
    public String getName()
    {
      return this.name;
    }
    public String getJob()
    {
        return this.job;

    }
}
