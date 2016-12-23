![alt tag](https://raw.githubusercontent.com/anderson-uchoa/DyMMer/dev/images/Logo DyMMer.png)


# **DyMMer** #
**feature models, variability, dynamic software product lines, quality measures, automated reasoning, non-functional requirements, visualization measures**

DyMMer tool was developed to extract measures from different feature models, which can be described using the file format proposed by the S.P.L.O.T. feature models repository. The S.P.L.O.T. repository has over 600 models entered by SPL community to download, edit or product configuration. In S.P.L.O.T, a feature model is represented by a XML format and stored as a file. These files contain a header and the feature model relations (body). The header contains information about authorship, authorship contact and affiliation, besides the feature model creation date. However, the header information is not mandatory, it can be left in blank. The body of the file is mandatory and comprises the feature model structure and its integrity constraints. So, DyMMer receives as input XML files. The DyMMer process a feature model le and creates an in memory model that make possible to compute the quality measures values. Currently, DyMMer tool is able to collect 41 different quality measures in an automatic manner.



DyMMer uses JxBrowser http://www.teamdev.com/jxbrowser, which is a proprietary software. The use of JxBrowser is governed by JxBrowser Product Licence Agreement http://www.teamdev.com/jxbrowser-licence-agreement. If you would like to use JxBrowser in your development, please contact TeamDev.
