# MSDF Icons Usage Guide

## Overview
MSDF иконки теперь доступны для использования в GUI компонентах через класс `Fonts.ICONS`.

## Загрузка шрифта иконок

```java
import dev.mark.system.font.Fonts;
import dev.mark.system.font.MSDFFont;

// Получить шрифт иконок
MSDFFont iconFont = Fonts.ICONS.get();
```

## Использование иконок

### Вариант 1: Использование констант из класса Icons

```java
import dev.mark.system.font.Icons;
import dev.mark.system.font.Fonts;
import dev.mark.system.render.TextCache;
import dev.mark.system.render.BuiltText;

// Получить шрифт иконок
MSDFFont iconFont = Fonts.ICONS.get();

// Отрисовать иконку
float iconSize = 16.0F;
BuiltText iconText = TextCache.a(iconFont, Icons.ICON_5, iconSize, color);
iconText.a(matrix4f, x, y, alpha);
```

### Вариант 2: Прямое использование символов

```java
// Использовать символы напрямую
String icon = "5";  // Иконка с unicode 53
BuiltText iconText = TextCache.a(iconFont, icon, iconSize, color);
iconText.a(matrix4f, x, y, alpha);
```

## Доступные иконки

Иконки маппятся на следующие символы:
- Цифры: `0-9` (unicode 48-57)
- Специальные: `:` (58), `;` (59)
- Буквы: `A-Z` (65-87, не все буквы доступны)

Полный список констант доступен в классе `Icons`.

## Пример: Кнопка с иконкой

```java
import dev.mark.system.font.Fonts;
import dev.mark.system.font.Icons;
import dev.mark.system.font.MSDFFont;

public class IconButtonExample extends Component {
    private final MSDFFont iconFont = Fonts.ICONS.get();
    private final String icon = Icons.ICON_5;
    
    @Override
    protected void a(Matrix4f matrix4f, float f, float f1, int i, int j, float f3, float f2) {
        // Отрисовать фон кнопки
        // ...
        
        // Отрисовать иконку
        float iconSize = 12.0F;
        BuiltText iconText = TextCache.a(iconFont, icon, iconSize, color);
        float iconX = f + (width - iconFont.c(icon, iconSize)) / 2.0F;
        float iconY = f1 + (height - iconSize) / 2.0F;
        iconText.a(matrix4f, iconX, iconY, f2);
    }
}
```

## Пример: Иконка с текстом

```java
public void renderIconWithText(Matrix4f matrix4f, float x, float y, float alpha) {
    MSDFFont iconFont = Fonts.ICONS.get();
    MSDFFont textFont = Fonts.B.get();
    
    float iconSize = 10.0F;
    float textSize = 8.0F;
    float spacing = 4.0F;
    
    // Отрисовать иконку
    BuiltText iconText = TextCache.a(iconFont, Icons.ICON_D, iconSize, color);
    iconText.a(matrix4f, x, y, alpha);
    
    // Отрисовать текст рядом с иконкой
    float textX = x + iconFont.c(Icons.ICON_D, iconSize) + spacing;
    BuiltText text = TextCache.a(textFont, "Settings", textSize, color);
    text.a(matrix4f, textX, y, alpha);
}
```

## Размеры иконок

Рекомендуемые размеры для разных контекстов:
- Маленькие иконки (в тексте): 6-8
- Средние иконки (кнопки): 10-14
- Большие иконки (заголовки): 16-20

## Цвета

Иконки поддерживают любые цвета через параметр `color` в `TextCache.a()`:

```java
import java.awt.Color;

Color iconColor = new Color(255, 255, 255, 200);
BuiltText iconText = TextCache.a(iconFont, Icons.ICON_V, iconSize, iconColor);
```
