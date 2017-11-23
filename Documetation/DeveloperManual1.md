# Developer Manual
-----

## 1. Bulid Environment

### 1.1. IDE and OS
```
- IDE : Android Studio
- IDE Version : 3.0
- OS : Ubuntu 14.04 LTS, Mac, Windows, etc.
```

### 1.2. SDK version
```
- Complie SDK Version : 26
- Build Tools Version : 26.0.2

- Min SDK Version : 22
- Target SDK Version : 26
```

## 2. Code Convention

### 2.1. In Java file

- Naming Variables

```
- Primitive Variables and non-Widget Objects : Snake Casing
- Widget Objects : Snake Casing With Shorten Class Name
- Method : Camel Casing
- Parameters in Method : Camel Casing
```

- Indentation

```
- K&R Indent Style
```

- Comments

```
- Defualt : Must put Comment at Not-Overrided Methods.
- Contents : Description, Paramters, Returns.
```

- Examples
```java
private TextView tv_text;
private int positionX, positionY;
//...some task...

/**
  * Set Text in TextView to "x, y : printToText"
  * @param x is value of x-axis.
  * @param y is value of y-axis.
  * @param printToText is text to print in TextView
  */
private void printText(int x, int y, String printToText){
	positionX = x;
	positionY = y;
	tv_text.setText(positionX + ", "+ positionY + ":" + printToText);
}
```

### 2.2. In Layout file

- Naming Widget IDs
```
- Casing : Snake Casing
- Form : (Activity Name)_(Shorten Class Name)_(Feature)
```

- Indentation
Indentation is formed like following.
```

```

- Comments
```
- Defualt : Put Comments if the content changes between above and below.
- Contents : Text which represents following contents.
```

- Examples

### 2.3. In Value file

- Naming Value IDs

- Indentation

- Comments

- Examples

## 3. Contributing

Please give our a message by Slack.

## Appendix A. Casing

## Appendix B. Shorten Class Name

## Appendix C. K&R Indent Style

