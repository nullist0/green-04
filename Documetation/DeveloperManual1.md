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
- Form : (Activity or View Name)_(Shorten Class Name)_(Feature)
```

- Indentation

Indentation is formed like in examples.

- Comments

```
- Defualt : Put Comments if the content changes between above and below.
- Contents : Text which represents following contents.
```

- Examples

```xml
<LinearLayout
	android:id="@+id/main_ll_test"
	android:layout_width="wrap_content"
	android:layout_height="wrap_content">
	<TextView
		android:id="@+id/main_tv_hello"
		android:text="@string/hello"/>
</LinearLayout>
```

### 2.3. In Value file

- Naming Value IDs

```
- Casing : Snake Casing
- Form : (Type where the value uses)_(Name where the value uses)_(Feature)
```

- Indentation

Indentation for Value files follows the same as Layout File Indentation.
Please see examples in this Section 2.3.

- Comments

Comments also follow the same as Layout File Comments.  
So, following is same as Layout.

```
- Defualt : Put Comments if the content changes between above and below.
- Contents : Text which represents following contents.
```

- Examples

```xml
<resources>
	<!-- CardActivity -->
	<!-- View Strings -->
	<string name="card_count">%1$d th card</string>
	
	<!-- Process Strings -->
	<string name="card_image">Image</string>
	<string name="card_text">Text</string>
	<string name="card_template">Template</string>
</resources>
```

## 3. Contributing

Please give our a message by Slack.

## Appendix A. Casing

## Appendix B. Shorten Class Name

## Appendix C. K&R Indent Style

